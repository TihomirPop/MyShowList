package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.ReviewText;
import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.domain.model.UserId;

public record SaveReviewCommand(
        UserId userId,
        ShowId showId,
        ReviewText reviewText
) {

}
