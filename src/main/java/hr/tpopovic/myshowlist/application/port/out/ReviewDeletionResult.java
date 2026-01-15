package hr.tpopovic.myshowlist.application.port.out;

public sealed interface ReviewDeletionResult {

    record Success() implements ReviewDeletionResult {

    }

    record NotFound() implements ReviewDeletionResult {

    }

    record Failure() implements ReviewDeletionResult {

    }
}
