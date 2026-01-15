package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record Review(
        ShowId showId,
        ReviewText reviewText
) {

    public Review {
        requireNonNull(showId, "showId");
        requireNonNull(reviewText, "reviewText");
    }
}
