package hr.tpopovic.myshowlist.application.port.out;

public interface ForSavingReview {

    SaveReviewResult save(SaveReviewCommand command);
}
