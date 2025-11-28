package hr.tpopovic.myshowlist.adapter.out;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

@ConfigurationProperties(prefix = "service.jwt")
public record JwtProperties(
        String base64Secret,
        Duration expiration
) {

    public JwtProperties {
        requireNonNull(base64Secret, "base64Secret must not be null");
        requireNonNull(expiration, "Expiration must not be null");

        if(base64Secret.isBlank()) {
            throw new IllegalArgumentException("base64secret must not be blank");
        }
    }
}
