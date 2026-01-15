package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.UserId;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import hr.tpopovic.myshowlist.application.port.out.FetchUserWithRoleResult;
import hr.tpopovic.myshowlist.application.port.out.ForFetchingUserWithRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserWithRoleFetcher implements ForFetchingUserWithRole {

    private static final Logger log = LoggerFactory.getLogger(UserWithRoleFetcher.class);

    private final UserRepository userRepository;

    public UserWithRoleFetcher(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FetchUserWithRoleResult fetch(Username username) {
        try {
            return userRepository.findByUsername(username.value())
                    .<FetchUserWithRoleResult>map(entity -> new FetchUserWithRoleResult.Success(
                            new UserId(entity.getId()),
                            entity.getRole()
                    ))
                    .orElseGet(FetchUserWithRoleResult.UserNotFound::new);
        } catch (Exception e) {
            log.error("Error fetching user with role for username: {}", username.value(), e);
            return new FetchUserWithRoleResult.Failure();
        }
    }
}
