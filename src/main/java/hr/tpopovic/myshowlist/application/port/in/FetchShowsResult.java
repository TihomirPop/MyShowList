package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.Show;

import java.util.List;

import static java.util.Objects.requireNonNull;

public sealed interface FetchShowsResult {

    record Success(List<Show> shows) implements FetchShowsResult {

        public Success {
            requireNonNull(shows, "shows");
        }

    }
}
