package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.Movie;
import hr.tpopovic.myshowlist.application.domain.model.TvSeries;

import java.util.List;

public sealed interface SearchExternalShowsResult {
    record Success(List<TvSeries> tvSeries, List<Movie> movies) implements SearchExternalShowsResult {
        public Success {
            if (tvSeries == null) {
                throw new IllegalArgumentException("tvSeries cannot be null");
            }
            if (movies == null) {
                throw new IllegalArgumentException("movies cannot be null");
            }
        }
    }

    record Failure(String message) implements SearchExternalShowsResult {
        public Failure {
            if (message == null || message.isBlank()) {
                throw new IllegalArgumentException("message cannot be null or blank");
            }
        }
    }
}
