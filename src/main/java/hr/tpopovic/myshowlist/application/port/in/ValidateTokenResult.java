package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.Username;

import static java.util.Objects.requireNonNull;

public sealed interface ValidateTokenResult {

    record Valid(Username username) implements ValidateTokenResult{

        public Valid {
            requireNonNull(username, "username must not be null");
        }

    }

    record Invalid() implements ValidateTokenResult{

    }

    record Failure() implements ValidateTokenResult{

    }

}
