package hr.tpopovic.myshowlist.application.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReviewTextTest {

    @Test
    void should_create_valid_review_text() {
        // given
        String validText = "This is a great show!";

        // when
        ReviewText reviewText = new ReviewText(validText);

        // then
        assertThat(reviewText.value()).isEqualTo(validText);
    }

    @Test
    void should_reject_null_value() {
        // when & then
        assertThatThrownBy(() -> new ReviewText(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Review text cannot be null or blank");
    }

    @Test
    void should_reject_blank_value() {
        // when & then
        assertThatThrownBy(() -> new ReviewText("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Review text cannot be null or blank");
    }

    @Test
    void should_reject_empty_value() {
        // when & then
        assertThatThrownBy(() -> new ReviewText(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Review text cannot be null or blank");
    }

    @Test
    void should_reject_value_exceeding_max_length() {
        // given
        String tooLongText = "a".repeat(10001);

        // when & then
        assertThatThrownBy(() -> new ReviewText(tooLongText))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Review text cannot exceed 10000 characters");
    }

    @Test
    void should_accept_value_at_max_length() {
        // given
        String maxLengthText = "a".repeat(10000);

        // when
        ReviewText reviewText = new ReviewText(maxLengthText);

        // then
        assertThat(reviewText.value()).hasSize(10000);
    }
}
