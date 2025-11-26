package hr.tpopovic.myshowlist.adapter.in.show;

import hr.tpopovic.myshowlist.application.domain.model.Genre;
import hr.tpopovic.myshowlist.application.domain.model.Movie;
import hr.tpopovic.myshowlist.application.domain.model.Show;
import hr.tpopovic.myshowlist.application.domain.model.TvSeries;

import java.util.Set;
import java.util.stream.Collectors;

public class ShowDtoMapper {

    private ShowDtoMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    static ShowDto toDto(Show show) {
        return switch (show) {
            case Movie movie -> movie(movie);
            case TvSeries tvSeries -> tvSeries(tvSeries);
        };
    }

    private static ShowDto movie(Movie movie) {
        return new MovieDto(
                movie.id().id().toString(),
                movie.title().name(),
                movie.description().text(),
                getGenres(movie),
                movie.releaseDate()
        );
    }

    private static ShowDto tvSeries(TvSeries tvSeries) {
        return new TvSeriesDto(
                tvSeries.id().id().toString(),
                tvSeries.title().name(),
                tvSeries.description().text(),
                getGenres(tvSeries),
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
