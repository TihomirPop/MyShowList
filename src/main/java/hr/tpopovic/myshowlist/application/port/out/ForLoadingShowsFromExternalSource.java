package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Movie;
import hr.tpopovic.myshowlist.application.domain.model.TvSeries;

import java.util.List;

public interface ForLoadingShowsFromExternalSource {

    List<TvSeries> loadTvSeries(String query);
    List<Movie> loadMovies(String query);

}
