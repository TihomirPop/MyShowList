package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.domain.model.UserId;

public interface ForDeletingReview {

    DeleteReviewResult delete(UserId userId, ShowId showId);
}
