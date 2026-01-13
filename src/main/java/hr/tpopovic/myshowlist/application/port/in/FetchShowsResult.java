package hr.tpopovic.myshowlist.application.port.in;

import java.util.List;

import static java.util.Objects.requireNonNull;

public sealed interface FetchShowsResult {

    record Success(List<ShowDetails> shows) implements FetchShowsResult {

        public Success {
            requireNonNull(shows, "shows");
        }

    }

    record Failure() implements FetchShowsResult {

    }

}
