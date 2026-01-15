package hr.tpopovic.myshowlist.application.port.in;

public sealed interface DeleteReviewResult {

    record Success() implements DeleteReviewResult {

    }

    record UserNotFound() implements DeleteReviewResult {

    }

    record ReviewNotFound() implements DeleteReviewResult {

    }

    record Failure() implements DeleteReviewResult {

    }
}
