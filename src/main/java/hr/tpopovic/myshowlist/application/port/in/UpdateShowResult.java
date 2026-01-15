package hr.tpopovic.myshowlist.application.port.in;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public sealed interface UpdateShowResult {

    record Success() implements UpdateShowResult {
    }

    record ShowNotFound() implements UpdateShowResult {
    }

    record GenresNotFound(Set<String> missingGenres) implements UpdateShowResult {
        public GenresNotFound {
            requireNonNull(missingGenres, "missingGenres must not be null");
        }
    }

    record Failure(String message) implements UpdateShowResult {
    }

}
