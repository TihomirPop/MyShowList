package hr.tpopovic.myshowlist.adapter.in;

import java.time.LocalDate;

public final class TvSeriesDto extends ShowDto {

    private final Integer episodeCount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public TvSeriesDto(
            String id,
            String title,
            String description,
            Integer episodeCount,
            LocalDate startDate,
            LocalDate endDate
    ) {
        super(id, title, description);
        this.episodeCount = episodeCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
