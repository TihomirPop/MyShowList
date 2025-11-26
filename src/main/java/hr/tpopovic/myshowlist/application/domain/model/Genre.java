package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public record Genre(String name) {

    public Genre {
        requireNonNull(name, "name");
    }

}
