package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.adapter.out.show.ShowLoader;
import hr.tpopovic.myshowlist.adapter.out.show.ShowRepository;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OutAdapterConfig {

    @Bean
    public ForLoadingShows forLoadingShows(ShowRepository showRepository) {
        return new ShowLoader(showRepository);
    }

}
