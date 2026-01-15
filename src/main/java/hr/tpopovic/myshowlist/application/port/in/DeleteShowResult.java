package hr.tpopovic.myshowlist.application.port.in;

public sealed interface DeleteShowResult {

    record Success() implements DeleteShowResult {
    }

    record ShowNotFound() implements DeleteShowResult {
    }

    record Failure(String message) implements DeleteShowResult {
    }

}
