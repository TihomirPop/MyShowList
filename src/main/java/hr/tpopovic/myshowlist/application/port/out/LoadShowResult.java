package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Show;

import static java.util.Objects.requireNonNull;

public sealed interface LoadShowResult {

    record Success(Show show) implements LoadShowResult {

        public Success {
            requireNonNull(show, "show must not be null");
        }

    }

    record NotFound() implements LoadShowResult {

    }

    record Failure() implements LoadShowResult {

    }
}
