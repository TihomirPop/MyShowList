package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.Movie;
import hr.tpopovic.myshowlist.application.domain.model.TvSeries;
import hr.tpopovic.myshowlist.application.port.in.SearchExternalShows;
import hr.tpopovic.myshowlist.application.port.in.SearchExternalShowsResult;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShowsFromExternalSource;

import java.util.List;

public class ExternalSearchService implements SearchExternalShows {
    private static final int RESULTS_LIMIT = 5;

    private final ForLoadingShowsFromExternalSource forLoadingShowsFromExternalSource;

    public ExternalSearchService(ForLoadingShowsFromExternalSource forLoadingShowsFromExternalSource) {
        this.forLoadingShowsFromExternalSource = forLoadingShowsFromExternalSource;
    }

    @Override
    public SearchExternalShowsResult search(String query) {
        try {
            List<TvSeries> tvSeries = forLoadingShowsFromExternalSource.loadTvSeries(query)
                    .stream()
                    .limit(RESULTS_LIMIT)
                    .toList();

            List<Movie> movies = forLoadingShowsFromExternalSource.loadMovies(query)
                    .stream()
                    .limit(RESULTS_LIMIT)
                    .toList();

            return new SearchExternalShowsResult.Success(tvSeries, movies);
        } catch (Exception e) {
            return new SearchExternalShowsResult.Failure("Failed to search external shows: " + e.getMessage());
        }
    }
}
