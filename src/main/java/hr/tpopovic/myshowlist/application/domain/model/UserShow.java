package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record UserShow(
        Show show,
        Progress progress,
        Status status,
        Score score
) {

    public UserShow {
        requireNonNull(show, "show");
        requireNonNull(progress, "progress");
        requireNonNull(status, "status");
        requireNonNull(score, "score");
    }
}
