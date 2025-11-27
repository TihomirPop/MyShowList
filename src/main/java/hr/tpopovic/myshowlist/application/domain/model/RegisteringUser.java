package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record RegisteringUser(
        Username username,
        Password password
) {

    public RegisteringUser {
        requireNonNull(username, "username");
        requireNonNull(password, "password");
    }

}
