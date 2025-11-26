package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.adapter.out.show.MovieEntity;
import hr.tpopovic.myshowlist.adapter.out.show.ShowEntityMapper;
import hr.tpopovic.myshowlist.adapter.out.show.TvSeriesEntity;
import hr.tpopovic.myshowlist.application.domain.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

class ShowEntityMapperTest {

    @Test
    void should_map_movie_entity_to_movie() {
        // given
        var movieEntity = new MovieEntity();
        UUID id = UUID.randomUUID();
        movieEntity.setId(id);
        movieEntity.setTitle("Inception");
        movieEntity.setDescription("A mind-bending thriller");
        movieEntity.setGenres(Set.of());
        movieEntity.setReleaseDate(LocalDate.of(2010, 7, 16));

        // when
        Show show = ShowEntityMapper.toDomain(movieEntity);

        // then
        assertThat(show)
                .isNotNull()
                .asInstanceOf(type(Movie.class))
                .extracting(
                        Movie::id,
                        Movie::title,
                        Movie::description,
                        Movie::releaseDate
                )
                .containsExactly(
                        new ShowId(UUID.fromString(id.toString())),
                        new Title("Inception"),
                        new Description("A mind-bending thriller"),
                        LocalDate.of(2010, 7, 16)
                );
    }

    @Test
    void should_map_tv_series_entity_to_tv_series() {
        // given
        var tvSeriesEntity = new TvSeriesEntity();
        UUID id = UUID.randomUUID();
        tvSeriesEntity.setId(id);
        tvSeriesEntity.setTitle("Breaking Bad");
        tvSeriesEntity.setDescription("A high school chemistry teacher turned methamphetamine producer.");
        tvSeriesEntity.setGenres(Set.of());
        tvSeriesEntity.setEpisodeCount(62);
        tvSeriesEntity.setStartedDate(LocalDate.of(2008, 1, 20));
        tvSeriesEntity.setEndedDate(LocalDate.of(2013, 9, 29));

        // when
        Show show = ShowEntityMapper.toDomain(tvSeriesEntity);

        // then
        assertThat(show)
                .isNotNull()
                .asInstanceOf(type(TvSeries.class))
                .extracting(
                        TvSeries::id,
                        TvSeries::title,
                        TvSeries::description,
                        TvSeries::episodeCount,
                        TvSeries::airingPeriod
                )
                .containsExactly(
                        new ShowId(UUID.fromString(id.toString())),
                        new Title("Breaking Bad"),
                        new Description("A high school chemistry teacher turned methamphetamine producer."),
                        new EpisodeCount(62),
                        DateRange.from(LocalDate.of(2008, 1, 20))
                                .to(LocalDate.of(2013, 9, 29))
                );
    }

}