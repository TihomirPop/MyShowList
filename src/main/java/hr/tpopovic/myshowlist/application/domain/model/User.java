package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record User(
        Username username,
        HashedPassword hashedPassword
) {

    public User {
        requireNonNull(username, "username");
        requireNonNull(hashedPassword, "hashedPassword");
    }

}
