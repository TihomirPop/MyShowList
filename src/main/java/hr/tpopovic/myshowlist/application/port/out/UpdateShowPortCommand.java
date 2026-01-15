package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Show;

import static java.util.Objects.requireNonNull;

public record UpdateShowPortCommand(Show show) {
    public UpdateShowPortCommand {
        requireNonNull(show, "show must not be null");
    }
}
