package hr.tpopovic.myshowlist.adapter.out;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ReviewId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "show_id", nullable = false)
    private UUID showId;

    public ReviewId() {
    }

    public ReviewId(Integer userId, UUID showId) {
        this.userId = userId;
        this.showId = showId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UUID getShowId() {
        return showId;
    }

    public void setShowId(UUID showId) {
        this.showId = showId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(userId, reviewId.userId) &&
                Objects.equals(showId, reviewId.showId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, showId);
    }
}
