package hr.tpopovic.myshowlist.adapter.out.show;

import hr.tpopovic.myshowlist.application.domain.model.Genre;
import hr.tpopovic.myshowlist.application.domain.model.Movie;
import hr.tpopovic.myshowlist.application.domain.model.Show;
import hr.tpopovic.myshowlist.application.domain.model.TvSeries;
import hr.tpopovic.myshowlist.application.port.out.ForSavingShow;
import hr.tpopovic.myshowlist.application.port.out.SaveShowCommand;
import hr.tpopovic.myshowlist.application.port.out.SaveShowResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShowSaver implements ForSavingShow {

    private final ShowRepository showRepository;
    private final GenreRepository genreRepository;

    public ShowSaver(ShowRepository showRepository, GenreRepository genreRepository) {
        this.showRepository = showRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public SaveShowResult save(SaveShowCommand command) {
        try {
            Show show = command.show();
            ShowEntity entity = createEntityFromShow(show);
            showRepository.save(entity);
            return new SaveShowResult.Success(show.id());
        } catch (Exception e) {
            return new SaveShowResult.Failure("Failed to save show: " + e.getMessage());
        }
    }

    private ShowEntity createEntityFromShow(Show show) {
        Set<GenreEntity> genreEntities = loadGenreEntities(show.genres());

        return switch (show) {
            case Movie movie -> {
                MovieEntity entity = new MovieEntity();
                entity.setId(movie.id().id());
                entity.setTitle(movie.title().name());
                entity.setDescription(movie.description().text());
                entity.setGenres(genreEntities);
                entity.setReleaseDate(movie.releaseDate());
                yield entity;
            }
            case TvSeries tvSeries -> {
                TvSeriesEntity entity = new TvSeriesEntity();
                entity.setId(tvSeries.id().id());
                entity.setTitle(tvSeries.title().name());
                entity.setDescription(tvSeries.description().text());
                entity.setGenres(genreEntities);
                entity.setEpisodeCount(tvSeries.episodeCount().count());
                entity.setStartedDate(tvSeries.airingPeriod().from());
                entity.setEndedDate(tvSeries.airingPeriod().to());
                yield entity;
            }
        };
    }

    private Set<GenreEntity> loadGenreEntities(Set<Genre> genres) {
        Set<String> genreNames = genres.stream()
                .map(Genre::name)
                .collect(Collectors.toSet());

        List<GenreEntity> entities = genreRepository.findByNameIn(genreNames);
        return new HashSet<>(entities);
    }

}
