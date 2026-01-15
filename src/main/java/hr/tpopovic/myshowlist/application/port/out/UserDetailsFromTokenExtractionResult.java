package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Role;
import hr.tpopovic.myshowlist.application.domain.model.Username;

public sealed interface UserDetailsFromTokenExtractionResult {
    record Success(Username username, Role role) implements UserDetailsFromTokenExtractionResult {}
    record Failure() implements UserDetailsFromTokenExtractionResult {}
}
