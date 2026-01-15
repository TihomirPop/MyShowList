package hr.tpopovic.myshowlist.config;

import hr.tpopovic.myshowlist.application.domain.service.AuthService;
import hr.tpopovic.myshowlist.application.domain.service.ReviewService;
import hr.tpopovic.myshowlist.application.domain.service.ShowService;
import hr.tpopovic.myshowlist.application.domain.service.UserShowService;
import hr.tpopovic.myshowlist.application.port.in.FetchShows;
import hr.tpopovic.myshowlist.application.port.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public FetchShows fetchShows(ForLoadingShows forLoadingShows, ForFetchingScore forFetchingScore) {
        return new ShowService(forLoadingShows, forFetchingScore);
    }

    @Bean
    public AuthService authService(
            ForHashingPassword forHashingPassword,
            ForSavingUser forSavingUser,
            ForFetchingPasswordHash forFetchingPasswordHash,
            ForFetchingUserWithRole forFetchingUserWithRole,
            ForCheckingPassword forCheckingPassword,
            ForGeneratingToken forGeneratingToken,
            ForValidatingToken forValidatingToken,
            ForExtractingUserDetailsFromToken forExtractingUserDetailsFromToken
    ) {
        return new AuthService(
                forHashingPassword,
                forSavingUser,
                forFetchingPasswordHash,
                forFetchingUserWithRole,
                forCheckingPassword,
                forGeneratingToken,
                forValidatingToken,
                forExtractingUserDetailsFromToken
        );
    }

    @Bean
    public UserShowService userShowService(
            ForLoadingShows forLoadingShows,
            ForFetchingUser forFetchingUser,
            ForSavingUserShow forSavingUserShow,
            ForLoadingUserShows forLoadingUserShows,
            ForFetchingScore forFetchingScore
    ) {
        return new UserShowService(forLoadingShows, forFetchingUser, forSavingUserShow, forLoadingUserShows, forFetchingScore);
    }

    @Bean
    public ReviewService reviewService(
            ForLoadingShows forLoadingShows,
            ForFetchingUser forFetchingUser,
            ForSavingReview forSavingReview,
            ForLoadingReviews forLoadingReviews,
            ForDeletingReview forDeletingReview,
            ForLoadingUsers forLoadingUsers
    ) {
        return new ReviewService(
                forLoadingShows,
                forFetchingUser,
                forSavingReview,
                forLoadingReviews,
                forDeletingReview,
                forLoadingUsers
        );
    }
}
