package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.Review;
import hr.tpopovic.myshowlist.application.domain.model.Username;

public record ReviewDetails(
        Review review,
        Username username
) {

}
