package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.*;

import java.time.LocalDate;
import java.util.Set;

public sealed interface CreateShowCommand {

    Title title();

    Description description();

    Set<Genre> genres();

    record CreateMovie(
            Title title,
            Description description,
            Set<Genre> genres,
            LocalDate releaseDate
    ) implements CreateShowCommand {
    }

    record CreateTvSeries(
            Title title,
            Description description,
            Set<Genre> genres,
            EpisodeCount episodeCount,
            DateRange airingPeriod
    ) implements CreateShowCommand {
    }

}
