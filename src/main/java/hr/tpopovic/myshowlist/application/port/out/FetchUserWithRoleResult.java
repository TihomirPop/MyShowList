package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Role;
import hr.tpopovic.myshowlist.application.domain.model.UserId;

public sealed interface FetchUserWithRoleResult {
    record Success(UserId userId, Role role) implements FetchUserWithRoleResult {}
    record UserNotFound() implements FetchUserWithRoleResult {}
    record Failure() implements FetchUserWithRoleResult {}
}
