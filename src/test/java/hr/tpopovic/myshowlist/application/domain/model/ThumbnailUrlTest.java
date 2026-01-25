package hr.tpopovic.myshowlist.application.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThumbnailUrlTest {

    @Test
    void should_create_thumbnail_url_with_valid_https_url() {
        // given
        String url = "https://example.com/thumbnail.png";

        // when
        ThumbnailUrl thumbnailUrl = new ThumbnailUrl(url);

        // then
        assertThat(thumbnailUrl.url()).isEqualTo(url);
    }

    @Test
    void should_create_thumbnail_url_with_valid_http_url() {
        // given
        String url = "http://example.com/thumbnail.png";

        // when
        ThumbnailUrl thumbnailUrl = new ThumbnailUrl(url);

        // then
        assertThat(thumbnailUrl.url()).isEqualTo(url);
    }

    @Test
    void should_throw_exception_when_url_is_null() {
        // given
        String url = null;

        // when & then
        assertThatThrownBy(() -> new ThumbnailUrl(url))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("url must not be null");
    }

    @Test
    void should_throw_exception_when_url_is_blank() {
        // given
        String url = "   ";

        // when & then
        assertThatThrownBy(() -> new ThumbnailUrl(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Thumbnail URL cannot be blank");
    }

    @Test
    void should_throw_exception_when_url_is_relative() {
        // given
        String url = "/relative/path/thumbnail.png";

        // when & then
        assertThatThrownBy(() -> new ThumbnailUrl(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Thumbnail URL must be an absolute URL");
    }

    @Test
    void should_throw_exception_when_url_has_invalid_scheme() {
        // given
        String url = "ftp://example.com/thumbnail.png";

        // when & then
        assertThatThrownBy(() -> new ThumbnailUrl(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Thumbnail URL must use http or https scheme");
    }

    @Test
    void should_throw_exception_when_url_is_malformed() {
        // given
        String url = "not a valid url";

        // when & then
        assertThatThrownBy(() -> new ThumbnailUrl(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Thumbnail URL must be a valid URI");
    }

    @Test
    void should_throw_exception_when_url_exceeds_max_length() {
        // given
        String url = "https://example.com/" + "a".repeat(2048);

        // when & then
        assertThatThrownBy(() -> new ThumbnailUrl(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Thumbnail URL cannot exceed 2048 characters");
    }

}
