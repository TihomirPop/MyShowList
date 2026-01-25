package hr.tpopovic.myshowlist.application.domain.model;

import java.time.LocalDate;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public final class Movie extends Show {

    private final LocalDate releaseDate;

    public Movie(ShowId id, Title title, Description description, Set<Genre> genres, ThumbnailUrl thumbnailUrl, LocalDate releaseDate) {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");
        requireNonNull(genres, "genres");
        requireNonNull(thumbnailUrl, "thumbnailUrl");
        requireNonNull(releaseDate, "releaseDate");

        super(id, title, description, Set.copyOf(genres), thumbnailUrl);
        this.releaseDate = releaseDate;
    }

    public LocalDate releaseDate() {
        return releaseDate;
    }

    @Override
    public boolean tooManyEpisodesWatched(Progress watched) {
        return watched.value() > 1;
    }

}
