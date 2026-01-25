package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.*;

import java.time.LocalDate;
import java.util.Set;

public sealed interface CreateShowCommand {

    Title title();

    Description description();

    Set<Genre> genres();

    ThumbnailUrl thumbnailUrl();

    record CreateMovie(
            Title title,
            Description description,
            Set<Genre> genres,
            ThumbnailUrl thumbnailUrl,
            LocalDate releaseDate
    ) implements CreateShowCommand {
    }

    record CreateTvSeries(
            Title title,
            Description description,
            Set<Genre> genres,
            ThumbnailUrl thumbnailUrl,
            EpisodeCount episodeCount,
            DateRange airingPeriod
    ) implements CreateShowCommand {
    }

}
