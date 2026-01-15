package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.Review;
import hr.tpopovic.myshowlist.application.domain.model.ReviewText;
import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.domain.model.UserId;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingReviews;
import hr.tpopovic.myshowlist.application.port.out.LoadReviewsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReviewsLoader implements ForLoadingReviews {

    private static final Logger log = LoggerFactory.getLogger(ReviewsLoader.class);

    private final ReviewRepository reviewRepository;

    public ReviewsLoader(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public LoadReviewsResult loadByShow(ShowId showId) {
        try {
            List<Review> reviews = reviewRepository.findByShowId(showId.id())
                    .stream()
                    .map(entity -> new Review(
                            new UserId(entity.getId().getUserId()),
                            new ShowId(entity.getId().getShowId()),
                            new ReviewText(entity.getReviewText())
                    ))
                    .toList();

            return new LoadReviewsResult.Success(reviews);
        } catch (Exception e) {
            log.error("Error loading reviews for show ID: {}", showId.id(), e);
            return new LoadReviewsResult.Failure();
        }
    }
}
