package hr.tpopovic.myshowlist.adapter.in.show;

import hr.tpopovic.myshowlist.application.port.in.FetchShows;
import hr.tpopovic.myshowlist.application.port.in.FetchShowsResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service.api.root-path}${service.api.show.path}")
public class ShowController {

    private final FetchShows fetchShows;

    public ShowController(FetchShows fetchShows) {
        this.fetchShows = fetchShows;
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

}
