package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.ReviewText;
import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.domain.model.Username;

public record UpsertReviewCommand(
        Username username,
        ShowId showId,
        ReviewText reviewText
) {

}
