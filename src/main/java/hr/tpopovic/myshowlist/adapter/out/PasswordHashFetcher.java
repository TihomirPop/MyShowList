package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.HashedPassword;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import hr.tpopovic.myshowlist.application.port.out.FetchPasswordHashResult;
import hr.tpopovic.myshowlist.application.port.out.ForFetchingPasswordHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PasswordHashFetcher implements ForFetchingPasswordHash {

    private static final Logger log = LoggerFactory.getLogger(PasswordHashFetcher.class);

    private final UserRepository userRepository;

    public PasswordHashFetcher(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FetchPasswordHashResult fetch(Username username) {
        Optional<UserEntity> maybeUserEntity;
        try {
            maybeUserEntity = userRepository.findByUsername(username.value());
        } catch (Exception e) {
            log.error("Error fetching password hash for username: {}", username.value(), e);
            return new FetchPasswordHashResult.Failure();
        }

        if(maybeUserEntity.isEmpty()) {
            return new FetchPasswordHashResult.NotFound();
        }

        UserEntity userEntity = maybeUserEntity.get();

        return new FetchPasswordHashResult.Success(
                new HashedPassword(userEntity.getPasswordHash())
        );
    }

}
