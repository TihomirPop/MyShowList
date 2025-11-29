package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public sealed interface Score {

    record Rated(Integer value) implements Score {

        public Rated {
            requireNonNull(value, "Score value cannot be null");

            if (value < 1 || value > 10) {
                throw new IllegalArgumentException("Score value must be between 1 and 10");
            }
        }

    }

    record NotRated() implements Score {

    }

}
