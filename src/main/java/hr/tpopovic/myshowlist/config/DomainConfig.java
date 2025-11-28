package hr.tpopovic.myshowlist.config;

import hr.tpopovic.myshowlist.application.domain.model.Token;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import hr.tpopovic.myshowlist.application.domain.service.AuthService;
import hr.tpopovic.myshowlist.application.domain.service.ShowService;
import hr.tpopovic.myshowlist.application.port.in.FetchShows;
import hr.tpopovic.myshowlist.application.port.in.RegisterUser;
import hr.tpopovic.myshowlist.application.port.out.FetchPasswordHashResult;
import hr.tpopovic.myshowlist.application.port.out.ForHashingPassword;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShows;
import hr.tpopovic.myshowlist.application.port.out.ForSavingUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public FetchShows fetchShows(ForLoadingShows forLoadingShows) {
        return new ShowService(forLoadingShows);
    }

    @Bean
    public RegisterUser registerUser(ForHashingPassword forHashingPassword, ForSavingUser forSavingUser) {
        return new AuthService(
                forHashingPassword,
                forSavingUser,
                        (_) -> new FetchPasswordHashResult.Failure(),
                (_, __) -> false,
                (Username _) -> new Token("dummy-token")
        );
    }
}
