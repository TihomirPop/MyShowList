package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.ShowId;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public sealed interface CreateShowResult {

    record Success(ShowId showId) implements CreateShowResult {
        public Success {
            requireNonNull(showId, "showId must not be null");
        }
    }

    record GenresNotFound(Set<String> missingGenres) implements CreateShowResult {
        public GenresNotFound {
            requireNonNull(missingGenres, "missingGenres must not be null");
        }
    }

    record Failure(String message) implements CreateShowResult {
    }

}
