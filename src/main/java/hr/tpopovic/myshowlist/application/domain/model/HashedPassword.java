package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record HashedPassword(
        String value
) {

    public HashedPassword {
        requireNonNull(value, "value");

        if(value.isBlank()) {
            throw new IllegalArgumentException("Hashed password cannot be blank");
        }
    }
}
