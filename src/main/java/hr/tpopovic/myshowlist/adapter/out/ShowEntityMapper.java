package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.*;

public class ShowEntityMapper {

    private ShowEntityMapper() {
    }

    public static Show toDomain(ShowEntity showEntity) {
        return switch (showEntity) {
            case MovieEntity movieEntity -> toDomain(movieEntity);
            case TvSeriesEntity tvSeriesEntity -> toDomain(tvSeriesEntity);
            default -> throw new IllegalStateException("Unexpected value: " + showEntity);
        };
    }

    private static Movie toDomain(MovieEntity movieEntity) {
        return new Movie(
                new ShowId(movieEntity.getId()),
                new Title(movieEntity.getTitle()),
                new Description(movieEntity.getDescription()),
                movieEntity.getReleaseDate()
        );
    }

    private static TvSeries toDomain(TvSeriesEntity tvSeriesEntity) {
        return new TvSeries(
                new ShowId(tvSeriesEntity.getId()),
                new Title(tvSeriesEntity.getTitle()),
                new Description(tvSeriesEntity.getDescription()),
                new EpisodeCount(tvSeriesEntity.getEpisodeCount()),
                DateRange.from(tvSeriesEntity.getStartedDate()).to(tvSeriesEntity.getEndedDate())
        );
    }
}
