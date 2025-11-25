package hr.tpopovic.myshowlist.application.domain.model;

import static java.util.Objects.requireNonNull;

public final class TvSeries extends Show {

    private final EpisodeCount episodeCount;
    private final DateRange airingPeriod;

    public TvSeries(ShowId id, Title title, Description description, EpisodeCount episodeCount, DateRange airingPeriod) {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");
        requireNonNull(episodeCount, "id");
        requireNonNull(airingPeriod, "airingPeriod");

        super(id, title, description);
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
