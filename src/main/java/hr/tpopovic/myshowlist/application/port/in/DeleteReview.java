package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.domain.model.Username;

public interface DeleteReview {

    DeleteReviewResult delete(Username username, ShowId showId);
}
