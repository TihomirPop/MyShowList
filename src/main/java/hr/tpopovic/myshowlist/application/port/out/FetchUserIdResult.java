package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.UserId;

public sealed interface FetchUserIdResult {

    record Success(UserId userId) implements FetchUserIdResult {

    }

    record UserNotFound() implements FetchUserIdResult {

    }

    record Failure() implements FetchUserIdResult {

    }

}
