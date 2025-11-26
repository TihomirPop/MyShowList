package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.*;

import java.util.Set;
import java.util.stream.Collectors;

public class ShowEntityMapper {

    private ShowEntityMapper() {
    }

    public static Show toDomain(ShowEntity showEntity) {
        return switch (showEntity) {
            case MovieEntity movieEntity -> movieEntity(movieEntity);
            case TvSeriesEntity tvSeriesEntity -> tvSeriesEntity(tvSeriesEntity);
            default -> throw new IllegalStateException("Unexpected value: " + showEntity);
        };
    }

    private static Movie movieEntity(MovieEntity movieEntity) {
        return new Movie(
                new ShowId(movieEntity.getId()),
                new Title(movieEntity.getTitle()),
                new Description(movieEntity.getDescription()),
                getGenres(movieEntity),
                movieEntity.getReleaseDate()
        );
    }

    private static TvSeries tvSeriesEntity(TvSeriesEntity tvSeriesEntity) {
        return new TvSeries(
                new ShowId(tvSeriesEntity.getId()),
                new Title(tvSeriesEntity.getTitle()),
                new Description(tvSeriesEntity.getDescription()),
                getGenres(tvSeriesEntity),
                new EpisodeCount(tvSeriesEntity.getEpisodeCount()),
                DateRange.from(tvSeriesEntity.getStartedDate()).to(tvSeriesEntity.getEndedDate())
        );
    }

    private static Set<Genre> getGenres(ShowEntity showEntity) {
        return showEntity.getGenres()
                .stream()
                .map(GenreEntity::getName)
                .map(Genre::new)
                .collect(Collectors.toSet());
    }
}
