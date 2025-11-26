package hr.tpopovic.myshowlist.config;

import hr.tpopovic.myshowlist.application.domain.service.ShowService;
import hr.tpopovic.myshowlist.application.port.in.FetchShows;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public FetchShows fetchShows(ForLoadingShows forLoadingShows) {
        return new ShowService(forLoadingShows);
    }
}
