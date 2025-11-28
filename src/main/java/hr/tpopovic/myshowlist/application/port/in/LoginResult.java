package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.Token;

public sealed interface LoginResult {

    record Success(Token token) implements LoginResult {
    }

    record WrongCredentials() implements LoginResult {
    }

    record Failure() implements LoginResult {
    }

}
