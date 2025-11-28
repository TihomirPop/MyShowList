package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.Password;
import hr.tpopovic.myshowlist.application.domain.model.Username;

import static java.util.Objects.requireNonNull;

public record RegisterCommand(
        Username username,
        Password password
) {

    public RegisterCommand {
        requireNonNull(username, "username");
        requireNonNull(password, "password");
    }

}
