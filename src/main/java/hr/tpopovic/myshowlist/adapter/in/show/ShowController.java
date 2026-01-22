package hr.tpopovic.myshowlist.adapter.in.show;

import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.port.in.FetchShow;
import hr.tpopovic.myshowlist.application.port.in.FetchShowResult;
import hr.tpopovic.myshowlist.application.port.in.FetchShows;
import hr.tpopovic.myshowlist.application.port.in.FetchShowsResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${service.api.show.path}")
public class ShowController {

    private final FetchShows fetchShows;
    private final FetchShow fetchShow;

    public ShowController(FetchShows fetchShows, FetchShow fetchShow) {
        this.fetchShows = fetchShows;
        this.fetchShow = fetchShow;
    }

    @GetMapping
    ResponseEntity<ShowResponse> getShows() {
        FetchShowsResult result = fetchShows.fetchShows();
        ShowResponse response = ShowResponseMapper.toResponse(result);

        return switch (response) {
            case ShowResponse.Ok ok -> ResponseEntity.ok(ok);
            case ShowResponse.Failure failure -> ResponseEntity.internalServerError().body(failure);
        };
    }

    @GetMapping("/{showId}")
    ResponseEntity<SingleShowResponse> getShow(@PathVariable String showId) {
        ShowId id = new ShowId(UUID.fromString(showId));
        FetchShowResult result = fetchShow.fetchShow(id);
        SingleShowResponse response = SingleShowResponseMapper.toResponse(result);

        return switch (response) {
            case SingleShowResponse.Ok ok -> ResponseEntity.ok(ok);
            case SingleShowResponse.NotFound _ -> ResponseEntity.notFound().build();
            case SingleShowResponse.Failure failure -> ResponseEntity.internalServerError().body(failure);
        };
    }

}
