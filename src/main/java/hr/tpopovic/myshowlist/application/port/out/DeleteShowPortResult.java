package hr.tpopovic.myshowlist.application.port.out;

public sealed interface DeleteShowPortResult {

    record Success() implements DeleteShowPortResult {
    }

    record ShowNotFound() implements DeleteShowPortResult {
    }

    record Failure(String message) implements DeleteShowPortResult {
    }

}
