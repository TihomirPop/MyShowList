package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record Username(
        String value
) {

    public Username {
        requireNonNull(value, "value");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if (value.length() < 3 || value.length() > 42) {
            throw new IllegalArgumentException("Username must be between 3 and 42 characters long");
        }
    }

}
