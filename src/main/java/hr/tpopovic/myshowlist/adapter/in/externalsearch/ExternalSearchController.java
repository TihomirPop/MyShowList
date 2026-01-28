package hr.tpopovic.myshowlist.adapter.in.externalsearch;

import hr.tpopovic.myshowlist.application.port.in.SearchExternalShows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service.api.external-search.path}")
public class ExternalSearchController {
    private final SearchExternalShows searchExternalShows;

    public ExternalSearchController(SearchExternalShows searchExternalShows) {
        this.searchExternalShows = searchExternalShows;
    }

    @GetMapping
    public ResponseEntity<ExternalSearchResponse> search(@RequestParam String query) {
        var result = searchExternalShows.search(query);
        var response = ExternalSearchResponseMapper.toResponse(result);

        return switch (response) {
            case ExternalSearchResponse.Ok ok -> ResponseEntity.ok(ok);
            case ExternalSearchResponse.Failure failure ->
                    ResponseEntity.internalServerError().body(failure);
        };
    }
}
