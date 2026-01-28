package hr.tpopovic.myshowlist.application.domain.model;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public record DateRange(ShowDate from, ShowDate to) {

    public DateRange {
        requireNonNull(from, "from");
        requireNonNull(to, "to");

        if (from instanceof ShowDate.Known(LocalDate fromDate) && to instanceof ShowDate.Known(LocalDate toDate)) {
            if (toDate.isBefore(fromDate)) {
                throw new IllegalArgumentException("to must not be before from");
            }
        }
    }

    public static Builder from(ShowDate from) {
        return new Builder(from);
    }

    public static class Builder {
        private final ShowDate from;

        private Builder(ShowDate from) {
            this.from = from;
        }

        public DateRange to(ShowDate to) {
            return new DateRange(from, to);
        }
    }
}