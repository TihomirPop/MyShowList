package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record AverageScore(Double score) {

    public AverageScore {
        requireNonNull(score, "Score cannot be null");

        if (score < 0.0 || score > 10.0) {
            throw new IllegalArgumentException("Score must be between 0.0 and 10.0");
        }
    }
}
