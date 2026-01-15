package hr.tpopovic.myshowlist.application.port.out;

public sealed interface DeleteReviewResult {

    record Success() implements DeleteReviewResult {

    }

    record NotFound() implements DeleteReviewResult {

    }

    record Failure() implements DeleteReviewResult {

    }
}
