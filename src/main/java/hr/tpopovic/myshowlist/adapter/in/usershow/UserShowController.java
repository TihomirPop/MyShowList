package hr.tpopovic.myshowlist.adapter.in.usershow;

import hr.tpopovic.myshowlist.adapter.in.FailedValidationResponse;
import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.AddUserShow;
import hr.tpopovic.myshowlist.application.port.in.AddUserShowCommand;
import hr.tpopovic.myshowlist.application.port.in.AddUserShowResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("${service.api.root-path}${service.api.user-show.path}")
public class UserShowController {

    private final AddUserShow addUserShow;

    public UserShowController() {
        this.addUserShow = (_) -> new AddUserShowResult.Failure();
    }

    @PostMapping
    public ResponseEntity<AddUserShowResponse> addUserShow(
            @RequestBody AddUserShowRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        AddUserShowCommand addUserShowCommand = new AddUserShowCommand(
                new Username(userDetails.getUsername()),
                new ShowId(UUID.fromString(request.showId())),
                new Progress(request.progress()),
                mapStatus(request.status()),
                mapScore(request.score())
        );

        AddUserShowResult result = addUserShow.add(addUserShowCommand);

        return switch (result) {
            case AddUserShowResult.Success _ -> ResponseEntity.status(HttpStatus.CREATED).build();
            case AddUserShowResult.DuplicateEntry _ -> ResponseEntity.status(HttpStatus.CONFLICT).build();
            case AddUserShowResult.ShowNotFound _ -> ResponseEntity.notFound().build();
            case AddUserShowResult.Failure _ -> ResponseEntity.internalServerError().build();
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
