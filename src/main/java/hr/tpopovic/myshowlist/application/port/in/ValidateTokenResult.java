package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.Role;
import hr.tpopovic.myshowlist.application.domain.model.Username;

import static java.util.Objects.requireNonNull;

public sealed interface ValidateTokenResult {

    record Valid(Username username, Role role) implements ValidateTokenResult{

        public Valid {
            requireNonNull(username, "username must not be null");
            requireNonNull(role, "role must not be null");
        }

    }

    record Invalid() implements ValidateTokenResult{

    }

    record Failure() implements ValidateTokenResult{

    }

}
