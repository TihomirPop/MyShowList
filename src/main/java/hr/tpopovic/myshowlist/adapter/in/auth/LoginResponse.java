package hr.tpopovic.myshowlist.adapter.in.auth;

public interface LoginResponse {

    record Ok(String token) implements LoginResponse {

    }

    record Error(String message) implements LoginResponse {

    }

}
