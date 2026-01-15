package hr.tpopovic.myshowlist.adapter.in.show;

import hr.tpopovic.myshowlist.adapter.in.FailedValidationResponse;
import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${service.api.show.path}")
public class AdminShowController {

    private final CreateShow createShow;
    private final UpdateShow updateShow;
    private final DeleteShow deleteShow;

    public AdminShowController(CreateShow createShow, UpdateShow updateShow, DeleteShow deleteShow) {
        this.createShow = createShow;
        this.updateShow = updateShow;
        this.deleteShow = deleteShow;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createShow(@RequestBody CreateShowRequest request) {
        CreateShowCommand command = mapToCreateCommand(request);
        CreateShowResult result = createShow.create(command);

        return switch (result) {
            case CreateShowResult.Success(ShowId showId) ->
                    ResponseEntity.created(URI.create("/api/v1/shows/" + showId.id())).build();
            case CreateShowResult.GenresNotFound(Set<String> missingGenres) ->
                    ResponseEntity.badRequest()
                            .body(new FailedValidationResponse("Genres not found: " + String.join(", ", missingGenres)));
            case CreateShowResult.Failure(String message) ->
                    ResponseEntity.internalServerError()
                            .body(new FailedValidationResponse(message));
        };
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateShow(@PathVariable String id, @RequestBody UpdateShowRequest request) {
        if (!id.equals(request.getId())) {
            return ResponseEntity.badRequest()
                    .body(new FailedValidationResponse("Path ID does not match body ID"));
        }

        UpdateShowCommand command = mapToUpdateCommand(request);
        UpdateShowResult result = updateShow.update(command);

        return switch (result) {
            case UpdateShowResult.Success _ -> ResponseEntity.ok().build();
            case UpdateShowResult.ShowNotFound _ -> ResponseEntity.notFound().build();
            case UpdateShowResult.GenresNotFound(Set<String> missingGenres) ->
                    ResponseEntity.badRequest()
                            .body(new FailedValidationResponse("Genres not found: " + String.join(", ", missingGenres)));
            case UpdateShowResult.Failure(String message) ->
                    ResponseEntity.internalServerError()
                            .body(new FailedValidationResponse(message));
        };
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteShow(@PathVariable String id) {
        ShowId showId = new ShowId(UUID.fromString(id));
        DeleteShowResult result = deleteShow.delete(showId);

        return switch (result) {
            case DeleteShowResult.Success _ -> ResponseEntity.noContent().build();
            case DeleteShowResult.ShowNotFound _ -> ResponseEntity.notFound().build();
            case DeleteShowResult.Failure(String message) ->
                    ResponseEntity.internalServerError()
                            .body(new FailedValidationResponse(message));
        };
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<FailedValidationResponse> handleValidation(Exception e) {
        return ResponseEntity.badRequest()
                .body(new FailedValidationResponse(e.getMessage()));
    }

    private CreateShowCommand mapToCreateCommand(CreateShowRequest request) {
        Title title = new Title(request.getTitle());
        Description description = new Description(request.getDescription());
        Set<Genre> genres = request.getGenres().stream()
                .map(Genre::new)
                .collect(Collectors.toSet());

        return switch (request) {
            case CreateMovieRequest movieRequest ->
                    new CreateShowCommand.CreateMovie(title, description, genres, movieRequest.getReleaseDate());
            case CreateTvSeriesRequest tvSeriesRequest -> {
                EpisodeCount episodeCount = new EpisodeCount(tvSeriesRequest.getEpisodeCount());
                DateRange airingPeriod = DateRange.from(tvSeriesRequest.getStartDate()).to(tvSeriesRequest.getEndDate());
                yield new CreateShowCommand.CreateTvSeries(title, description, genres, episodeCount, airingPeriod);
            }
        };
    }

    private UpdateShowCommand mapToUpdateCommand(UpdateShowRequest request) {
        ShowId showId = new ShowId(UUID.fromString(request.getId()));
        Title title = new Title(request.getTitle());
        Description description = new Description(request.getDescription());
        Set<Genre> genres = request.getGenres().stream()
                .map(Genre::new)
                .collect(Collectors.toSet());

        return switch (request) {
            case UpdateMovieRequest movieRequest ->
                    new UpdateShowCommand.UpdateMovie(showId, title, description, genres, movieRequest.getReleaseDate());
            case UpdateTvSeriesRequest tvSeriesRequest -> {
                EpisodeCount episodeCount = new EpisodeCount(tvSeriesRequest.getEpisodeCount());
                DateRange airingPeriod = DateRange.from(tvSeriesRequest.getStartDate()).to(tvSeriesRequest.getEndDate());
                yield new UpdateShowCommand.UpdateTvSeries(showId, title, description, genres, episodeCount, airingPeriod);
            }
        };
    }

}
