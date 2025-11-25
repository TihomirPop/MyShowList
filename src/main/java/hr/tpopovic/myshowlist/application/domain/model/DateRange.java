package hr.tpopovic.myshowlist.application.domain.model;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public record DateRange(LocalDate from, LocalDate to) {

    public DateRange {
        requireNonNull(from, "from");
        requireNonNull(to, "to");
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("to must not be before from");
        }
    }

    public static Builder from(LocalDate from) {
        return new Builder(from);
    }

    public static class Builder {
        private final LocalDate from;

        private Builder(LocalDate from) {
            this.from = from;
        }

        public DateRange to(LocalDate to) {
            return new DateRange(from, to);
        }

    }

}
