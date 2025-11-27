package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.User;
import jakarta.validation.constraints.NotNull;

import static java.util.Objects.requireNonNull;

@NotNull
public sealed interface RegisterUserResult {

    record Success(User user) implements RegisterUserResult {

        public Success {
            requireNonNull(user, "user");
        }

    }

    record Failure() implements RegisterUserResult {

    }

}
