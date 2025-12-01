package hr.tpopovic.myshowlist.adapter.in.usershow;

import hr.tpopovic.myshowlist.adapter.in.FailedValidationResponse;
import hr.tpopovic.myshowlist.adapter.in.show.ShowDtoMapper;
import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("${service.api.root-path}${service.api.user-show.path}")
public class UserShowController {

    private final UpsertUserShow upsertUserShow;
    private final FetchUserShows fetchUserShows;

    public UserShowController(UpsertUserShow upsertUserShow) {
        this.upsertUserShow = upsertUserShow;
        this.fetchUserShows = (_) -> null;
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

    @GetMapping
    public ResponseEntity<GetUserShowsResponse> getUserShows(@AuthenticationPrincipal UserDetails userDetails) {
        Username username = new Username(userDetails.getUsername());
        FetchUserShowsResult result = fetchUserShows.fetch(username);

        return switch (result) {
            case FetchUserShowsResult.Success(List<UserShow> shows) -> mapUserShows(shows);
            case FetchUserShowsResult.UserNotFound _ -> ResponseEntity.notFound().build();
            case FetchUserShowsResult.Failure _ -> ResponseEntity.internalServerError().build();
        };
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<FailedValidationResponse> handleValidation(Exception e) {
        return ResponseEntity.badRequest()
                .body(new FailedValidationResponse(e.getMessage()));
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

    private ResponseEntity<GetUserShowsResponse> mapUserShows(List<UserShow> shows) {
        List<UserShowDto> dtos = shows.stream()
                .map(userShow -> new UserShowDto(
                        ShowDtoMapper.toDto(userShow.show()),
                        userShow.progress().value(),
                        mapStatus(userShow.status()),
                        mapScore(userShow.score())
                ))
                .toList();

        return ResponseEntity.ok(new GetUserShowsResponse.Success(dtos));
    }

    private String mapStatus(Status status) {
        return status.name()
                .toLowerCase()
                .replace('_', ' ');
    }

    private Short mapScore(Score score) {
        return switch (score) {
            case Score.Rated(Short value) -> value;
            case Score.NotRated _ -> 0;
        };
    }

}
