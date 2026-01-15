package hr.tpopovic.myshowlist.application.port.in;

public interface UpsertReview {

    UpsertReviewResult upsert(UpsertReviewCommand command);
}
