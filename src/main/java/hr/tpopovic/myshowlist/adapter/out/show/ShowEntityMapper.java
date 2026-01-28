package hr.tpopovic.myshowlist.adapter.out.show;

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
                new ThumbnailUrl(movieEntity.getThumbnailUrl()),
                movieEntity.getReleaseDate()
        );
    }

    private static TvSeries tvSeriesEntity(TvSeriesEntity tvSeriesEntity) {
        ShowDate fromDate = ShowDate.ofNullable(tvSeriesEntity.getStartedDate());
        ShowDate toDate = ShowDate.ofNullable(tvSeriesEntity.getEndedDate());
        DateRange airingPeriod = DateRange.from(fromDate).to(toDate);

        return new TvSeries(
                new ShowId(tvSeriesEntity.getId()),
                new Title(tvSeriesEntity.getTitle()),
                new Description(tvSeriesEntity.getDescription()),
                getGenres(tvSeriesEntity),
                new ThumbnailUrl(tvSeriesEntity.getThumbnailUrl()),
                new EpisodeCount(tvSeriesEntity.getEpisodeCount()),
                airingPeriod
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
