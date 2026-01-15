package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.*;
import hr.tpopovic.myshowlist.application.port.out.*;

import java.util.List;

public class ReviewService implements UpsertReview, FetchShowReviews, DeleteReview {

    private final ForLoadingShows forLoadingShows;
    private final ForFetchingUser forFetchingUser;
    private final ForSavingReview forSavingReview;
    private final ForLoadingReviews forLoadingReviews;
    private final ForDeletingReview forDeletingReview;
    private final ForLoadingUsers forLoadingUsers;

    public ReviewService(
            ForLoadingShows forLoadingShows,
            ForFetchingUser forFetchingUser,
            ForSavingReview forSavingReview,
            ForLoadingReviews forLoadingReviews,
            ForDeletingReview forDeletingReview,
            ForLoadingUsers forLoadingUsers
    ) {
        this.forLoadingShows = forLoadingShows;
        this.forFetchingUser = forFetchingUser;
        this.forSavingReview = forSavingReview;
        this.forLoadingReviews = forLoadingReviews;
        this.forDeletingReview = forDeletingReview;
        this.forLoadingUsers = forLoadingUsers;
    }

    @Override
    public UpsertReviewResult upsert(UpsertReviewCommand command) {
        LoadShowResult showResult = forLoadingShows.load(command.showId());

        return switch (showResult) {
            case LoadShowResult.Success _ -> handleShowLoaded(command);
            case LoadShowResult.NotFound _ -> new UpsertReviewResult.ShowNotFound();
            case LoadShowResult.Failure _ -> new UpsertReviewResult.Failure();
        };
    }

    private UpsertReviewResult handleShowLoaded(UpsertReviewCommand command) {
        FetchUserIdResult userResult = forFetchingUser.fetch(command.username());

        return switch (userResult) {
            case FetchUserIdResult.Success(UserId userId) -> handleUserFound(userId, command);
            case FetchUserIdResult.UserNotFound _ -> new UpsertReviewResult.UserNotFound();
            case FetchUserIdResult.Failure _ -> new UpsertReviewResult.Failure();
        };
    }

    private UpsertReviewResult handleUserFound(UserId userId, UpsertReviewCommand command) {
        var saveCommand = new SaveReviewCommand(
                userId,
                command.showId(),
                command.reviewText()
        );

        SaveReviewResult saveResult = forSavingReview.save(saveCommand);

        return switch (saveResult) {
            case SaveReviewResult.Success _ -> new UpsertReviewResult.Success();
            case SaveReviewResult.ShowNotFound _ -> new UpsertReviewResult.ShowNotFound();
            case SaveReviewResult.Failure _ -> new UpsertReviewResult.Failure();
        };
    }

    @Override
    public FetchShowReviewsResult fetch(ShowId showId) {
        LoadShowResult showResult = forLoadingShows.load(showId);

        return switch (showResult) {
            case LoadShowResult.Success _ -> handleShowFoundForFetch(showId);
            case LoadShowResult.NotFound _ -> new FetchShowReviewsResult.ShowNotFound();
            case LoadShowResult.Failure _ -> new FetchShowReviewsResult.Failure();
        };
    }

    private FetchShowReviewsResult handleShowFoundForFetch(ShowId showId) {
        LoadReviewsResult result = forLoadingReviews.loadByShow(showId);

        return switch (result) {
            case LoadReviewsResult.Success(List<Review> reviews) -> handleReviewsLoaded(reviews);
            case LoadReviewsResult.Failure _ -> new FetchShowReviewsResult.Failure();
        };
    }

    private FetchShowReviewsResult handleReviewsLoaded(List<Review> reviews) {
        List<ReviewDetails> reviewDetails = reviews.stream()
                .map(review -> {
                    Username username = forLoadingUsers.loadUsername(review.userId());
                    return new ReviewDetails(review, username);
                })
                .toList();

        return new FetchShowReviewsResult.Success(reviewDetails);
    }

    @Override
    public DeleteReviewResult delete(Username username, ShowId showId) {
        FetchUserIdResult userResult = forFetchingUser.fetch(username);

        return switch (userResult) {
            case FetchUserIdResult.Success(UserId userId) -> handleUserFoundForDelete(userId, showId);
            case FetchUserIdResult.UserNotFound _ -> new DeleteReviewResult.UserNotFound();
            case FetchUserIdResult.Failure _ -> new DeleteReviewResult.Failure();
        };
    }

    private DeleteReviewResult handleUserFoundForDelete(UserId userId, ShowId showId) {
        ReviewDeletionResult result = forDeletingReview.delete(userId, showId);

        return switch (result) {
            case ReviewDeletionResult.Success _ -> new DeleteReviewResult.Success();
            case ReviewDeletionResult.NotFound _ -> new DeleteReviewResult.ReviewNotFound();
            case ReviewDeletionResult.Failure _ -> new DeleteReviewResult.Failure();
        };
    }
}
