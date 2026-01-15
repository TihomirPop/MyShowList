package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Genre;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public sealed interface LoadGenresResult {

    record Success(Set<Genre> foundGenres, Set<String> missingGenres) implements LoadGenresResult {
        public Success {
            requireNonNull(foundGenres, "foundGenres must not be null");
            requireNonNull(missingGenres, "missingGenres must not be null");
        }
    }

    record Failure(String message) implements LoadGenresResult {
    }

}
