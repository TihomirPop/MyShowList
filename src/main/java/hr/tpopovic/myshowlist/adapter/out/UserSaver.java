package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.User;
import hr.tpopovic.myshowlist.application.port.out.ForSavingUser;
import hr.tpopovic.myshowlist.application.port.out.SavingUserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSaver implements ForSavingUser {

    private static final Logger log = LoggerFactory.getLogger(UserSaver.class);

    private final UserRepository userRepository;

    public UserSaver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SavingUserResult save(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.username().value());
        userEntity.setPasswordHash(user.hashedPassword().value());
        userEntity.setRole(user.role());
        return save(userEntity);
    }

    private SavingUserResult save(UserEntity userEntity) {
        try {
            userRepository.save(userEntity);
            return new SavingUserResult.Success();
        } catch (RuntimeException e) {
            log.error("Error saving user", e);
            return new SavingUserResult.Failure();
        }
    }

}
