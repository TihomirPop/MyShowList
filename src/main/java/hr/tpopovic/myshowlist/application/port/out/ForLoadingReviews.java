package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.ShowId;

public interface ForLoadingReviews {

    LoadReviewsResult loadByShow(ShowId showId);
}
