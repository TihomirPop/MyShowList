package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.adapter.out.show.ShowEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class ReviewEntity {

    @EmbeddedId
    private ReviewId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", insertable = false, updatable = false)
    private ShowEntity show;

    @Column(name = "review_text", nullable = false, columnDefinition = "TEXT")
    private String reviewText;

    public ReviewId getId() {
        return id;
    }

    public void setId(ReviewId id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ShowEntity getShow() {
        return show;
    }

    public void setShow(ShowEntity show) {
        this.show = show;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
