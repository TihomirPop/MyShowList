package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.HashedPassword;

import static java.util.Objects.requireNonNull;

public sealed interface FetchPasswordHashResult {

    record Success(HashedPassword hashedPassword) implements FetchPasswordHashResult {

        public Success {
            requireNonNull(hashedPassword, "hashedPassword must not be null");
        }

    }

    record NotFound() implements FetchPasswordHashResult {

    }

    record Failure() implements FetchPasswordHashResult {

    }

}
