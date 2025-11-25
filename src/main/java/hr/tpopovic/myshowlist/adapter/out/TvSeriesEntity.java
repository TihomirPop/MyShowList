package hr.tpopovic.myshowlist.adapter.out;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "tv_series")
public final class TvSeriesEntity extends ShowEntity {

    @Column(name = "episode_count", nullable = false)
    private Integer episodeCount;

    @Column(name = "started_date", nullable = false)
    private LocalDate startedDate;

    @Column(name = "ended_date", nullable = false)
    private LocalDate endedDate;

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodesCount) {
        this.episodeCount = episodesCount;
    }

    public LocalDate getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(LocalDate startedDate) {
        this.startedDate = startedDate;
    }

    public LocalDate getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(LocalDate endedDate) {
        this.endedDate = endedDate;
    }

}