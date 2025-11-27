package hr.tpopovic.myshowlist.application.port.in;

import jakarta.validation.constraints.NotNull;

@NotNull
public sealed interface RegisterUserResult {

    record Success() implements RegisterUserResult {

    }

    record Failure() implements RegisterUserResult {

    }

}
