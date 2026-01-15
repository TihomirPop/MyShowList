package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.ShowId;

import static java.util.Objects.requireNonNull;

public sealed interface SaveShowResult {

    record Success(ShowId showId) implements SaveShowResult {
        public Success {
            requireNonNull(showId, "showId must not be null");
        }
    }

    record Failure(String message) implements SaveShowResult {
    }

}
