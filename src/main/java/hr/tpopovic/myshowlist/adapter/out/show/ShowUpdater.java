package hr.tpopovic.myshowlist.adapter.out.show;

import hr.tpopovic.myshowlist.application.domain.model.Genre;
import hr.tpopovic.myshowlist.application.domain.model.Movie;
import hr.tpopovic.myshowlist.application.domain.model.Show;
import hr.tpopovic.myshowlist.application.domain.model.TvSeries;
import hr.tpopovic.myshowlist.application.port.out.ForUpdatingShow;
import hr.tpopovic.myshowlist.application.port.out.UpdateShowPortCommand;
import hr.tpopovic.myshowlist.application.port.out.UpdateShowPortResult;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ShowUpdater implements ForUpdatingShow {

    private final ShowRepository showRepository;
    private final GenreRepository genreRepository;

    public ShowUpdater(ShowRepository showRepository, GenreRepository genreRepository) {
        this.showRepository = showRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public UpdateShowPortResult update(UpdateShowPortCommand command) {
        try {
            Show show = command.show();
            Optional<ShowEntity> existingEntity = showRepository.findById(show.id().id());

            if (existingEntity.isEmpty()) {
                return new UpdateShowPortResult.ShowNotFound();
            }

            ShowEntity entity = existingEntity.get();

            if (!isTypeCompatible(show, entity)) {
                return new UpdateShowPortResult.Failure("Cannot change show type (Movie <-> TvSeries)");
            }

            updateEntityFromShow(entity, show);
            showRepository.save(entity);

            return new UpdateShowPortResult.Success();
        } catch (Exception e) {
            return new UpdateShowPortResult.Failure("Failed to update show: " + e.getMessage());
        }
    }

    private boolean isTypeCompatible(Show show, ShowEntity entity) {
        return switch (show) {
            case Movie _ -> entity instanceof MovieEntity;
            case TvSeries _ -> entity instanceof TvSeriesEntity;
        };
    }

    private void updateEntityFromShow(ShowEntity entity, Show show) {
        Set<GenreEntity> genreEntities = loadGenreEntities(show.genres());

        entity.setTitle(show.title().name());
        entity.setDescription(show.description().text());
        entity.setGenres(genreEntities);
        entity.setThumbnailUrl(show.thumbnailUrl().url());

        switch (show) {
            case Movie movie when entity instanceof MovieEntity movieEntity -> movieEntity.setReleaseDate(movie.releaseDate());
            case TvSeries tvSeries when entity instanceof TvSeriesEntity tvSeriesEntity -> {
                tvSeriesEntity.setEpisodeCount(tvSeries.episodeCount().count());
                tvSeriesEntity.setStartedDate(tvSeries.airingPeriod().from().toNullable());
                tvSeriesEntity.setEndedDate(tvSeries.airingPeriod().to().toNullable());
            }
            default -> throw new IllegalStateException("Incompatible show and entity types");
        }
    }

    private Set<GenreEntity> loadGenreEntities(Set<Genre> genres) {
        Set<String> genreNames = genres.stream()
                .map(Genre::name)
                .collect(Collectors.toSet());

        List<GenreEntity> entities = genreRepository.findByNameIn(genreNames);
        return new HashSet<>(entities);
    }

}
