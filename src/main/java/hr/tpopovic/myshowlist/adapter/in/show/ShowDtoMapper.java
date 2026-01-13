package hr.tpopovic.myshowlist.adapter.in.show;

import hr.tpopovic.myshowlist.application.domain.model.*;

import java.util.Set;
import java.util.stream.Collectors;

public class ShowDtoMapper {

    private ShowDtoMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static ShowDto toDto(Show show, AverageScore averageScore) {
        return switch (show) {
            case Movie movie -> movie(movie, averageScore);
            case TvSeries tvSeries -> tvSeries(tvSeries, averageScore);
        };
    }

    private static ShowDto movie(Movie movie, AverageScore averageScore) {
        return new MovieDto(
                movie.id().id().toString(),
                movie.title().name(),
                movie.description().text(),
                getGenres(movie),
                averageScore.score(),
                movie.releaseDate()
        );
    }

    private static ShowDto tvSeries(TvSeries tvSeries, AverageScore averageScore) {
        return new TvSeriesDto(
                tvSeries.id().id().toString(),
                tvSeries.title().name(),
                tvSeries.description().text(),
                getGenres(tvSeries),
                averageScore.score(),
                tvSeries.episodeCount().count(),
                tvSeries.airingPeriod().from(),
                tvSeries.airingPeriod().to()
        );
    }

    private static Set<String> getGenres(Show show) {
        return show.genres()
                .stream()
                .map(Genre::name)
                .collect(Collectors.toSet());
    }

}
