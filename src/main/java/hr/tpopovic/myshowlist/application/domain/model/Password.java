package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record Password(
        String value
) {

    public Password {
        requireNonNull(value, "value");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }

        if (value.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }

        if (value.chars().noneMatch(Character::isUpperCase)) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }

        if (value.chars().noneMatch(Character::isLowerCase)) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }

        if (value.chars().noneMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }
    }
}
