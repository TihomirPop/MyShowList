package hr.tpopovic.myshowlist.config;

import hr.tpopovic.myshowlist.application.domain.service.AuthService;
import hr.tpopovic.myshowlist.application.domain.service.ShowService;
import hr.tpopovic.myshowlist.application.domain.service.UserShowService;
import hr.tpopovic.myshowlist.application.port.in.AddUserShow;
import hr.tpopovic.myshowlist.application.port.in.FetchShows;
import hr.tpopovic.myshowlist.application.port.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public FetchShows fetchShows(ForLoadingShows forLoadingShows) {
        return new ShowService(forLoadingShows);
    }

    @Bean
    public AuthService authService(
            ForHashingPassword forHashingPassword,
            ForSavingUser forSavingUser,
            ForFetchingPasswordHash forFetchingPasswordHash,
            ForCheckingPassword forCheckingPassword,
            ForGeneratingToken forGeneratingToken,
            ForValidatingToken forValidatingToken,
            ForExtractingUsernameFromToken forExtractingUsernameFromToken
    ) {
        return new AuthService(
                forHashingPassword,
                forSavingUser,
                forFetchingPasswordHash,
                forCheckingPassword,
                forGeneratingToken,
                forValidatingToken,
                forExtractingUsernameFromToken
        );
    }

    @Bean
    public AddUserShow addUserShow(ForFetchingUser forFetchingUser, ForSavingUserShow forSavingUserShow) {
        return new UserShowService(forFetchingUser, forSavingUserShow);
    }
}
