package hr.tpopovic.myshowlist.adapter.in.auth;

public sealed interface RegisterResponse {

    record Created() implements RegisterResponse {

    }

    record Error(String message) implements RegisterResponse {

    }

}
