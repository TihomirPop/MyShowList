package hr.tpopovic.myshowlist.application.domain.model;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public final class Movie extends Show {

    private final LocalDate releaseDate;

    public Movie(ShowId id, Title title, Description description, LocalDate releaseDate) {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");
        requireNonNull(releaseDate, "releaseDate");

        super(id, title, description);
        this.releaseDate = releaseDate;
    }

    public LocalDate releaseDate() {
        return releaseDate;
    }
}
