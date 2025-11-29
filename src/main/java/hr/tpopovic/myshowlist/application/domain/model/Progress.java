package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record Progress(
        Integer value
) {

    public Progress {
        requireNonNull(value, "Progress value cannot be null");

        if (value < 0) {
            throw new IllegalArgumentException("Progress value cannot be negative");
        }
    }
}
