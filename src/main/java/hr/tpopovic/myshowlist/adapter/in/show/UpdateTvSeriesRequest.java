package hr.tpopovic.myshowlist.adapter.in.show;

import java.time.LocalDate;
import java.util.Set;

public final class UpdateTvSeriesRequest extends UpdateShowRequest {

    private Integer episodeCount;
    private LocalDate startDate;
    private LocalDate endDate;

    public UpdateTvSeriesRequest() {
    }

    public UpdateTvSeriesRequest(String id, String title, String description, Set<String> genres, String thumbnailUrl,
                                 Integer episodeCount, LocalDate startDate, LocalDate endDate) {
        super(id, title, description, genres, thumbnailUrl);
        this.episodeCount = episodeCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
