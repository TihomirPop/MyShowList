package hr.tpopovic.myshowlist.application.port.out;

public sealed interface SaveReviewResult {

    record Success() implements SaveReviewResult {

    }

    record ShowNotFound() implements SaveReviewResult {

    }

    record Failure() implements SaveReviewResult {

    }
}
