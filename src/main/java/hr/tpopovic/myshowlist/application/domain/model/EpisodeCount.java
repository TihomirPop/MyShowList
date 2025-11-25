package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record EpisodeCount(Integer count) {

    public EpisodeCount {
        requireNonNull(count, "count");

        if(count < 1) {
            throw new IllegalArgumentException("count must be positive");
        }
    }

}
