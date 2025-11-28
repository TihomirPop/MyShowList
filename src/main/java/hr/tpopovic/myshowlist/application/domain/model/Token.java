package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record Token(
        String value
) {

    public Token {
        requireNonNull(value, "Token value cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Token value cannot be blank");
        }
    }
}
