package hr.tpopovic.myshowlist.adapter.out.tmdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.tools.TmdbException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TmdbConfig {

    @Bean
    public TmdbApi tmdbApi(@Value("${service.tmdb.api-key}") String apiKey) throws TmdbException {
        TmdbApi tmdbApi = new TmdbApi(apiKey);
//        System.out.println(tmdbApi.getSearch().searchTv("Frieren", null, false, null, null, null).getResults());
        return tmdbApi;
    }

}
