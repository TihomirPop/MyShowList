package hr.tpopovic.myshowlist.application.domain.model;

import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Objects.requireNonNull;

public record ThumbnailUrl(String url) {
    private static final int MAX_URL_LENGTH = 2048;

    public ThumbnailUrl {
        requireNonNull(url, "url must not be null");

        if (url.isBlank()) {
            throw new IllegalArgumentException("Thumbnail URL cannot be blank");
        }

        if (url.length() > MAX_URL_LENGTH) {
            throw new IllegalArgumentException(
                    "Thumbnail URL cannot exceed %d characters, got %d".formatted(MAX_URL_LENGTH, url.length())
            );
        }

        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Thumbnail URL must be a valid URI: " + e.getMessage(), e);
        }

        if (!uri.isAbsolute()) {
            throw new IllegalArgumentException("Thumbnail URL must be an absolute URL");
        }

        String scheme = uri.getScheme();
        if (scheme == null || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))) {
            throw new IllegalArgumentException("Thumbnail URL must use http or https scheme, got: " + scheme);
        }
    }
}
