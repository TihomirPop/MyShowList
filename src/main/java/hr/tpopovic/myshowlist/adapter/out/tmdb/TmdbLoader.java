package hr.tpopovic.myshowlist.adapter.out.tmdb;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShowsFromExternalSource;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbGenre;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TmdbTvSeries;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TmdbLoader implements ForLoadingShowsFromExternalSource {

    private final TmdbSearch tmdbSearch;
    private final TmdbTvSeries tmdbTvSeries;
    private final Map<Integer, String> showGenres;
    private final Map<Integer, String> movieGenres;

    public TmdbLoader(TmdbApi tmdbApi) throws TmdbException {
        this.tmdbSearch = tmdbApi.getSearch();
        this.tmdbTvSeries = tmdbApi.getTvSeries();
        TmdbGenre tmdbGenre = tmdbApi.getGenre();
        this.showGenres = tmdbGenre.getTvList(null)
                .stream()
                .collect(Collectors.toMap(
                        info.movito.themoviedbapi.model.core.Genre::getId,
                        info.movito.themoviedbapi.model.core.Genre::getName
                ));
        this.movieGenres = tmdbGenre.getMovieList(null)
                .stream()
                .collect(Collectors.toMap(
                        info.movito.themoviedbapi.model.core.Genre::getId,
                        info.movito.themoviedbapi.model.core.Genre::getName
                ));
    }

    @Override
    public List<TvSeries> loadTvSeries(String query) {
        TvSeriesResultsPage page;
        try {
            page = tmdbSearch.searchTv(query, null, false, null, null, null);
        } catch (TmdbException e) {
            return Collections.emptyList();
        }

        return StreamSupport.stream(page.spliterator(), false)
                .limit(5)
                .map(this::mapToTvSeries)
                .toList();
    }

    @Override
    public List<Movie> loadMovies(String query) {
        MovieResultsPage page;
        try {
            page = tmdbSearch.searchMovie(query, null, null, null, null, null, null);
        } catch (TmdbException e) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(page.spliterator(), false)
                .limit(5)
                .map(this::mapToMovie)
                .toList();
    }

    private TvSeries mapToTvSeries(info.movito.themoviedbapi.model.core.TvSeries tmdbSeries) {
        ShowId showId = new ShowId(UUID.randomUUID());
        Title title = new Title(tmdbSeries.getName());
        Description description = new Description(
                tmdbSeries.getOverview() != null && !tmdbSeries.getOverview().isBlank()
                        ? tmdbSeries.getOverview()
                        : "Unknown description");
        Set<Genre> genres = tmdbSeries.getGenreIds()
            .stream()
            .map(showGenres::get)
            .filter(Objects::nonNull)
            .map(Genre::new)
            .collect(Collectors.toSet());
        ThumbnailUrl thumbnailUrl = new ThumbnailUrl(
            tmdbSeries.getPosterPath() != null
                ? "https://image.tmdb.org/t/p/w500" + tmdbSeries.getPosterPath()
                : "https://via.placeholder.com/500x750?text=No+Image"
        );

        TvSeriesDb details;
        try {
            details = tmdbTvSeries.getDetails(tmdbSeries.getId(), null);
        } catch (TmdbException e) {
            throw new RuntimeException("Failed to load TV series details from TMDB", e);
        }

        Integer numberOfEpisodes = details.getNumberOfEpisodes();
        EpisodeCount episodeCount = new EpisodeCount(
                numberOfEpisodes != null && numberOfEpisodes > 0
                        ? numberOfEpisodes
                        : 1
        );

        LocalDate firstAirDate;
        try {
            firstAirDate = LocalDate.parse(tmdbSeries.getFirstAirDate());
        } catch (Exception e) {
            firstAirDate = null;
        }

        LocalDate lastAirDate;
        try {
            lastAirDate = LocalDate.parse(details.getLastAirDate());
        } catch (Exception e) {
            lastAirDate = null;
        }

        ShowDate fromDate = ShowDate.ofNullable(firstAirDate);
        ShowDate toDate = ShowDate.ofNullable(lastAirDate);
        DateRange airingPeriod = DateRange.from(fromDate).to(toDate);

        return new TvSeries(showId, title, description, genres, thumbnailUrl, episodeCount, airingPeriod);
    }

    private Movie mapToMovie(info.movito.themoviedbapi.model.core.Movie tmdbMovie) {
        ShowId showId = new ShowId(UUID.randomUUID());
        Title title = new Title(tmdbMovie.getTitle());
        Description description = new Description(
                tmdbMovie.getOverview() != null && !tmdbMovie.getOverview().isBlank()
                        ? tmdbMovie.getOverview()
                        : "Unknown description");
        Set<Genre> genres = tmdbMovie.getGenreIds()
                .stream()
                .map(movieGenres::get)
                .filter(Objects::nonNull)
                .map(Genre::new)
                .collect(Collectors.toSet());
        ThumbnailUrl thumbnailUrl = new ThumbnailUrl(
                tmdbMovie.getPosterPath() != null
                        ? "https://image.tmdb.org/t/p/w500" + tmdbMovie.getPosterPath()
                        : "https://via.placeholder.com/500x750?text=No+Image"
        );

        LocalDate firstAirDate;
        try {
            firstAirDate = LocalDate.parse(tmdbMovie.getReleaseDate());;
        } catch (Exception e) {
            firstAirDate = LocalDate.now();
        }

        return new Movie(showId, title, description, genres, thumbnailUrl, firstAirDate);
    }

}
