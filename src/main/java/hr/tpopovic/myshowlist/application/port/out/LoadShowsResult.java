package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Show;

import java.util.List;

import static java.util.Objects.requireNonNull;

public sealed interface LoadShowsResult {

    record Success(List<Show> shows) implements LoadShowsResult {

        public Success {
            requireNonNull(shows, "shows");
        }

    }

    record Failure() implements LoadShowsResult {

    }
}
