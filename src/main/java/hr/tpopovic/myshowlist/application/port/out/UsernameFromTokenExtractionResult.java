package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Username;

import static java.util.Objects.requireNonNull;

public sealed interface UsernameFromTokenExtractionResult {

    record Success(Username username) implements UsernameFromTokenExtractionResult {

        public Success {
            requireNonNull(username, "username must not be null");
        }

    }

    record Failure() implements UsernameFromTokenExtractionResult {

    }

}
