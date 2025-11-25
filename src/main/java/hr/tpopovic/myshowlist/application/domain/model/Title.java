package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record Title(String name) {

    public Title {
        requireNonNull(name, "name must not be null");

        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }

        if (name.length() > 255) {
            throw new IllegalArgumentException("name must not be longer than 255 characters");
        }
    }

}
