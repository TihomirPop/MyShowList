package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.port.out.ForSavingReview;
import hr.tpopovic.myshowlist.application.port.out.SaveReviewCommand;
import hr.tpopovic.myshowlist.application.port.out.SaveReviewResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

public class ReviewSaver implements ForSavingReview {

    private static final Logger log = LoggerFactory.getLogger(ReviewSaver.class);

    private final ReviewRepository reviewRepository;

    public ReviewSaver(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public SaveReviewResult save(SaveReviewCommand command) {
        ReviewId reviewId = new ReviewId(
                command.userId().id(),
                command.showId().id()
        );

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(reviewId);
        reviewEntity.setReviewText(command.reviewText().value());

        try {
            reviewRepository.save(reviewEntity);
            return new SaveReviewResult.Success();
        } catch (DataIntegrityViolationException e) {
            return parseException(e);
        } catch (Exception e) {
            log.error("Error saving review for userId: {} and showId: {}",
                    command.userId().id(), command.showId().id(), e);
            return new SaveReviewResult.Failure();
        }
    }

    private static SaveReviewResult parseException(DataIntegrityViolationException e) {
        if (e.getMessage().contains("foreign key constraint")) {
            return new SaveReviewResult.ShowNotFound();
        }

        log.error("Data integrity violation error saving review", e);
        return new SaveReviewResult.Failure();
    }
}
