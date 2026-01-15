package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.UserId;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLoader implements ForLoadingUsers {

    private static final Logger log = LoggerFactory.getLogger(UserLoader.class);

    private final UserRepository userRepository;

    public UserLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Username loadUsername(UserId userId) {
        return userRepository.findById(userId.id())
                .map(UserEntity::getUsername)
                .map(Username::new)
                .orElseThrow(() -> new IllegalStateException("User not found for ID: " + userId.id()));
    }
}
