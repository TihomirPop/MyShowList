package hr.tpopovic.myshowlist.application.domain.model;

import java.time.LocalDate;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public sealed interface ShowDate {

    static ShowDate ofNullable(LocalDate date) {
        return isNull(date)
                ? new Unknown()
                : new Known(date);
    }

    default LocalDate toNullable() {
        return switch (this) {
            case Known(LocalDate date) -> date;
            case Unknown() -> null;
        };
    }

    record Unknown() implements ShowDate {}

    record Known(LocalDate date) implements ShowDate {
        public Known {
            requireNonNull(date, "date");
        }
    }
}