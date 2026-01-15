package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record Review(
        UserId userId,
        ShowId showId,
        ReviewText reviewText
) {

    public Review {
        requireNonNull(userId, "userId");
        requireNonNull(showId, "showId");
        requireNonNull(reviewText, "reviewText");
    }
}
