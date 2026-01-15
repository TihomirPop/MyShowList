package hr.tpopovic.myshowlist.application.port.in;

public sealed interface UpsertReviewResult {

    record Success() implements UpsertReviewResult {

    }

    record UserNotFound() implements UpsertReviewResult {

    }

    record ShowNotFound() implements UpsertReviewResult {

    }

    record Failure() implements UpsertReviewResult {

    }
}
