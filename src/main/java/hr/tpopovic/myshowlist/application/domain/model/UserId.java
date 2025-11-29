package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record UserId(
        Integer id
) {

    public UserId {
        requireNonNull(id, "UserId id cannot be null");

        if (id <= 0) {
            throw new IllegalArgumentException("UserId id must be positive");
        }
    }
}
