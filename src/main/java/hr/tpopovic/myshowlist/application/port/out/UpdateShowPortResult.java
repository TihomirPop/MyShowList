package hr.tpopovic.myshowlist.application.port.out;

public sealed interface UpdateShowPortResult {

    record Success() implements UpdateShowPortResult {
    }

    record ShowNotFound() implements UpdateShowPortResult {
    }

    record Failure(String message) implements UpdateShowPortResult {
    }

}
