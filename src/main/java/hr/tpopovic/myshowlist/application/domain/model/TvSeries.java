package hr.tpopovic.myshowlist.application.domain.model;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public final class TvSeries extends Show {

    private final EpisodeCount episodeCount;
    private final DateRange airingPeriod;

    public TvSeries(
            ShowId id,
            Title title,
            Description description,
            Set<Genre> genres,
            EpisodeCount episodeCount,
            DateRange airingPeriod
    ) {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");
        requireNonNull(genres, "genres");
        requireNonNull(episodeCount, "episodeCount");
        requireNonNull(airingPeriod, "airingPeriod");

        super(id, title, description, Set.copyOf(genres));
        this.episodeCount = episodeCount;
        this.airingPeriod = airingPeriod;
    }

    public EpisodeCount episodeCount() {
        return episodeCount;
    }

    public DateRange airingPeriod() {
        return airingPeriod;
    }
}
