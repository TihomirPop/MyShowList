package hr.tpopovic.myshowlist.adapter.in.usershow;

import hr.tpopovic.myshowlist.adapter.in.FailedValidationResponse;
import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.UpsertUserShow;
import hr.tpopovic.myshowlist.application.port.in.UpsertUserShowCommand;
import hr.tpopovic.myshowlist.application.port.in.UpsertUserShowResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("${service.api.root-path}${service.api.user-show.path}")
public class UserShowController {

    private final UpsertUserShow upsertUserShow;

    public UserShowController(UpsertUserShow upsertUserShow) {
        this.upsertUserShow = upsertUserShow;
    }

    @PostMapping
    public ResponseEntity<UpsertUserShowResponse> addUserShow(
            @RequestBody UpsertUserShowRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return upsert(request, userDetails);
    }

    @PutMapping
    public ResponseEntity<UpsertUserShowResponse> updateUserShow(
            @RequestBody UpsertUserShowRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return upsert(request, userDetails);
    }

    private ResponseEntity<UpsertUserShowResponse> upsert(UpsertUserShowRequest request, UserDetails userDetails) {
        UpsertUserShowCommand upsertUserShowCommand = new UpsertUserShowCommand(
                new Username(userDetails.getUsername()),
                new ShowId(UUID.fromString(request.showId())),
                new Progress(request.progress()),
                mapStatus(request.status()),
                mapScore(request.score())
        );

        UpsertUserShowResult result = upsertUserShow.upsert(upsertUserShowCommand);

        return switch (result) {
            case UpsertUserShowResult.Success _ -> ResponseEntity.ok().build();
            case UpsertUserShowResult.UserNotFound _, UpsertUserShowResult.ShowNotFound _ -> ResponseEntity.notFound().build();
            case UpsertUserShowResult.InvalidInput _ -> ResponseEntity.badRequest().build();
            case UpsertUserShowResult.Failure _ -> ResponseEntity.internalServerError().build();
        };
    }


    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<FailedValidationResponse> handleValidation(Exception e) {
        return ResponseEntity.badRequest()
                .body(new FailedValidationResponse(e.getMessage()));
    }

    private Status mapStatus(String status) {
        String normalizedStatus = status.trim()
                .replace(' ', '_')
                .toUpperCase();

        return Status.valueOf(normalizedStatus);
    }
    
    public Score mapScore(Short score) {
        if (isNull(score) || score == 0) {
            return new Score.NotRated();
        }
        
        return new Score.Rated(score);
    }

}
