package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.domain.model.UserId;
import hr.tpopovic.myshowlist.application.port.out.ForDeletingReview;
import hr.tpopovic.myshowlist.application.port.out.ReviewDeletionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReviewDeleter implements ForDeletingReview {

    private static final Logger log = LoggerFactory.getLogger(ReviewDeleter.class);

    private final ReviewRepository reviewRepository;

    public ReviewDeleter(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewDeletionResult delete(UserId userId, ShowId showId) {
        try {
            ReviewId reviewId = new ReviewId(userId.id(), showId.id());

            if (!reviewRepository.existsById(reviewId)) {
                return new ReviewDeletionResult.NotFound();
            }

            reviewRepository.deleteById(reviewId);
            return new ReviewDeletionResult.Success();
        } catch (Exception e) {
            log.error("Error deleting review for userId: {} and showId: {}",
                    userId.id(), showId.id(), e);
            return new ReviewDeletionResult.Failure();
        }
    }
}
