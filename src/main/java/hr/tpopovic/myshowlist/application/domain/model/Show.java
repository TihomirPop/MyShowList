package hr.tpopovic.myshowlist.application.domain.model;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public abstract sealed class Show permits Movie, TvSeries {

    private final ShowId id;
    private final Title title;
    private final Description description;
    private final Set<Genre> genres;

    protected Show(ShowId id, Title title, Description description, Set<Genre> genres) {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");
        requireNonNull(genres, "genres");

        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = Set.copyOf(genres);
    }

    public ShowId id() {
        return id;
    }

    public Title title() {
        return title;
    }

    public Description description() {
        return description;
    }

    public Set<Genre> genres() {
        return Set.copyOf(genres);
    }
}
