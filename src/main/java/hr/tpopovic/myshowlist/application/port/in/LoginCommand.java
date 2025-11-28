package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.Password;
import hr.tpopovic.myshowlist.application.domain.model.Username;

import static java.util.Objects.requireNonNull;

public record LoginCommand(Username username, Password password) {

    public LoginCommand {
        requireNonNull(username, "username must not be null");
        requireNonNull(password, "password must not be null");
    }

}
