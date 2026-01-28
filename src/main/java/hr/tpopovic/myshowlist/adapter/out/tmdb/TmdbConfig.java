package hr.tpopovic.myshowlist.adapter.out.tmdb;

import hr.tpopovic.myshowlist.application.port.out.ForLoadingShowsFromExternalSource;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.tools.TmdbException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TmdbConfig {

    @Bean
    public TmdbApi tmdbApi(@Value("${service.tmdb.api-key}") String apiKey) throws TmdbException {
        return new TmdbApi(apiKey);
    }

    @Bean
    public ForLoadingShowsFromExternalSource forLoadingShowsFromExternalSource(TmdbApi tmdbApi) throws TmdbException {
        return new TmdbLoader(tmdbApi);
    }

}
