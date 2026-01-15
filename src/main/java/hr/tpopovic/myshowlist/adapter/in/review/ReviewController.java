package hr.tpopovic.myshowlist.adapter.in.review;

import hr.tpopovic.myshowlist.adapter.in.FailedValidationResponse;
import hr.tpopovic.myshowlist.application.domain.model.ReviewText;
import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import hr.tpopovic.myshowlist.application.port.in.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${service.api.review.path}")
public class ReviewController {

    private final UpsertReview upsertReview;
    private final FetchShowReviews fetchShowReviews;
    private final DeleteReview deleteReview;

    public ReviewController(UpsertReview upsertReview, FetchShowReviews fetchShowReviews, DeleteReview deleteReview) {
        this.upsertReview = upsertReview;
        this.fetchShowReviews = fetchShowReviews;
        this.deleteReview = deleteReview;
    }

    @PostMapping
    public ResponseEntity<Void> addReview(
            @RequestBody ReviewDto reviewDto,
            @PathVariable String showId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return upsert(reviewDto, showId, userDetails);
    }

    @PutMapping
    public ResponseEntity<Void> updateReview(
            @RequestBody ReviewDto reviewDto,
            @PathVariable String showId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return upsert(reviewDto, showId, userDetails);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable String showId) {
        ShowId showIdObj = new ShowId(UUID.fromString(showId));
        FetchShowReviewsResult result = fetchShowReviews.fetch(showIdObj);

        return switch (result) {
            case FetchShowReviewsResult.Success(List<ReviewDetails> reviews) -> mapReviews(reviews);
            case FetchShowReviewsResult.ShowNotFound _ -> ResponseEntity.notFound().build();
            case FetchShowReviewsResult.Failure _ -> ResponseEntity.internalServerError().build();
        };
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReview(
            @PathVariable String showId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Username username = new Username(userDetails.getUsername());
        ShowId showIdObj = new ShowId(UUID.fromString(showId));

        DeleteReviewResult result = deleteReview.delete(username, showIdObj);

        return switch (result) {
            case DeleteReviewResult.Success _ -> ResponseEntity.noContent().build();
            case DeleteReviewResult.UserNotFound _, DeleteReviewResult.ReviewNotFound _ -> ResponseEntity.notFound().build();
            case DeleteReviewResult.Failure _ -> ResponseEntity.internalServerError().build();
        };
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<FailedValidationResponse> handleValidation(Exception e) {
        return ResponseEntity.badRequest()
                .body(new FailedValidationResponse(e.getMessage()));
    }

    private ResponseEntity<Void> upsert(ReviewDto reviewDto, String showId, UserDetails userDetails) {
        UpsertReviewCommand command = new UpsertReviewCommand(
                new Username(userDetails.getUsername()),
                new ShowId(UUID.fromString(showId)),
                new ReviewText(reviewDto.reviewText())
        );

        UpsertReviewResult result = upsertReview.upsert(command);

        return switch (result) {
            case UpsertReviewResult.Success _ -> ResponseEntity.ok().build();
            case UpsertReviewResult.UserNotFound _, UpsertReviewResult.ShowNotFound _ -> ResponseEntity.notFound().build();
            case UpsertReviewResult.Failure _ -> ResponseEntity.internalServerError().build();
        };
    }

    private ResponseEntity<List<ReviewDto>> mapReviews(List<ReviewDetails> reviews) {
        List<ReviewDto> dtos = reviews.stream()
                .map(details -> new ReviewDto(
                        details.review().reviewText().value(),
                        details.username().value()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }

}
