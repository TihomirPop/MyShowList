package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public abstract class Show {

    private final ShowId id;
    private final Title title;
    private final Description description;

    protected Show(ShowId id, Title title, Description description) {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");

        this.id = id;
        this.title = title;
        this.description = description;
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
}
