package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record Description(String text) {

    public Description {
        requireNonNull(text, "text");

        if(text.isBlank()) {
            throw new IllegalArgumentException("text must not be blank");
        }

        if(text.length() > 65535) {
            throw new IllegalArgumentException("text must not be longer than 1000 characters");
        }
    }

}
