package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.adapter.out.show.ShowLoader;
import hr.tpopovic.myshowlist.adapter.out.show.ShowRepository;
import hr.tpopovic.myshowlist.application.port.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class OutAdapterConfig {

    @Bean
    public ForLoadingShows forLoadingShows(ShowRepository showRepository) {
        return new ShowLoader(showRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ForHashingPassword forHashingPassword(PasswordEncoder passwordEncoder) {
        return new PasswordHasher(passwordEncoder);
    }

    @Bean
    public ForSavingUser forSavingUser(UserRepository userRepository) {
        return new UserSaver(userRepository);
    }

    @Bean
    public ForFetchingPasswordHash forFetchingPasswordHash(UserRepository userRepository) {
        return new PasswordHashFetcher(userRepository);
    }

    @Bean
    public ForFetchingUserWithRole forFetchingUserWithRole(UserRepository userRepository) {
        return new UserWithRoleFetcher(userRepository);
    }

    @Bean
    public ForCheckingPassword forCheckingPassword(PasswordEncoder passwordEncoder) {
        return new PasswordChecker(passwordEncoder);
    }

    @Bean
    public JwtTokenManager jwtTokenManager(JwtProperties properties) {
        return new JwtTokenManager(properties);
    }

    @Bean
    public UserFetcher userFetcher(UserRepository userRepository) {
        return new UserFetcher(userRepository);
    }

    @Bean
    public UserShowSaver userShowSaver(UserShowRepository userShowRepository) {
        return new UserShowSaver(userShowRepository);
    }

    @Bean
    public UserShowLoader userShowLoader(UserShowRepository userShowRepository) {
        return new UserShowLoader(userShowRepository);
    }

    @Bean
    public ForFetchingScore forFetchingScore(UserShowRepository userShowRepository) {
        return new ScoreFetcher(userShowRepository);
    }

    @Bean
    public ForSavingReview forSavingReview(ReviewRepository reviewRepository) {
        return new ReviewSaver(reviewRepository);
    }

    @Bean
    public ForLoadingReviews forLoadingReviews(ReviewRepository reviewRepository) {
        return new ReviewsLoader(reviewRepository);
    }

    @Bean
    public ForDeletingReview forDeletingReview(ReviewRepository reviewRepository) {
        return new ReviewDeleter(reviewRepository);
    }

    @Bean
    public ForLoadingUsers forLoadingUsers(UserRepository userRepository) {
        return new UserLoader(userRepository);
    }

}
