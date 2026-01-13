package hr.tpopovic.myshowlist.adapter.in.show;

import java.time.LocalDate;
import java.util.Set;

public final class TvSeriesDto extends ShowDto {

    private final Integer episodeCount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public TvSeriesDto(
            String id,
            String title,
            String description,
            Set<String> genres,
            Double averageScore,
            Integer episodeCount,
            LocalDate startDate,
            LocalDate endDate
    ) {
        super(id, title, description, genres, averageScore);
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
