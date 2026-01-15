package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Review;

import java.util.List;

public sealed interface LoadReviewsResult {

    record Success(List<Review> reviews) implements LoadReviewsResult {

    }

    record Failure() implements LoadReviewsResult {

    }
}
