package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.UserId;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import hr.tpopovic.myshowlist.application.port.out.FetchUserIdResult;
import hr.tpopovic.myshowlist.application.port.out.ForFetchingUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserFetcher implements ForFetchingUser {

    private static final Logger log = LoggerFactory.getLogger(UserFetcher.class);

    private final UserRepository userRepository;

    public UserFetcher(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FetchUserIdResult fetch(Username username) {
        try {
            var userEntityOpt = userRepository.findByUsername(username.value());
            if (userEntityOpt.isEmpty()) {
                return new FetchUserIdResult.UserNotFound();
            }

            var userEntity = userEntityOpt.get();
            var userId = new UserId(userEntity.getId());

            return new FetchUserIdResult.Success(userId);
        } catch (Exception e) {
            log.error("Error fetching user ID for username: {}", username.value(), e);
            return new FetchUserIdResult.Failure();
        }
    }

}
