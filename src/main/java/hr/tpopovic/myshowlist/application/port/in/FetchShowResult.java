package hr.tpopovic.myshowlist.application.port.in;

import static java.util.Objects.requireNonNull;

public sealed interface FetchShowResult {
    record Success(ShowDetails showDetails) implements FetchShowResult {
        public Success {
            requireNonNull(showDetails, "showDetails must not be null");
        }
    }

    record NotFound() implements FetchShowResult {}

    record Failure() implements FetchShowResult {}
}
