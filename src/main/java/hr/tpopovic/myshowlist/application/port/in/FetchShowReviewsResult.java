package hr.tpopovic.myshowlist.application.port.in;

import java.util.List;

public sealed interface FetchShowReviewsResult {

    record Success(List<ReviewDetails> reviews) implements FetchShowReviewsResult {

    }

    record ShowNotFound() implements FetchShowReviewsResult {

    }

    record Failure() implements FetchShowReviewsResult {

    }
}
