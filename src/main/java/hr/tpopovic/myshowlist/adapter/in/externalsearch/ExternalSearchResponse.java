package hr.tpopovic.myshowlist.adapter.in.externalsearch;

import hr.tpopovic.myshowlist.adapter.in.show.ShowDto;

import java.util.List;

public sealed interface ExternalSearchResponse {
    record Ok(List<ShowDto> tvSeries, List<ShowDto> movies) implements ExternalSearchResponse {
    }

    record Failure(String message) implements ExternalSearchResponse {
    }
}
