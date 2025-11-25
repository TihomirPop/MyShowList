package hr.tpopovic.myshowlist.application.domain.model;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record ShowId(UUID id) {

    public ShowId {
        requireNonNull(id, "id");
    }

}
