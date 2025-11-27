package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.adapter.out.show.ShowLoader;
import hr.tpopovic.myshowlist.adapter.out.show.ShowRepository;
import hr.tpopovic.myshowlist.application.port.out.ForHashingPassword;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShows;
import hr.tpopovic.myshowlist.application.port.out.ForSavingUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;

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

}
