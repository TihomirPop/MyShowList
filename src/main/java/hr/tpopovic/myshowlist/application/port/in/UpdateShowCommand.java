package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.*;

import java.time.LocalDate;
import java.util.Set;

public sealed interface UpdateShowCommand {

    ShowId showId();

    Title title();

    Description description();

    Set<Genre> genres();

    record UpdateMovie(
            ShowId showId,
            Title title,
            Description description,
            Set<Genre> genres,
            LocalDate releaseDate
    ) implements UpdateShowCommand {
    }

    record UpdateTvSeries(
            ShowId showId,
            Title title,
            Description description,
            Set<Genre> genres,
            EpisodeCount episodeCount,
            DateRange airingPeriod
    ) implements UpdateShowCommand {
    }

}
