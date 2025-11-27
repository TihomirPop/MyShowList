package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.HashedPassword;
import hr.tpopovic.myshowlist.application.domain.model.RegisteringUser;
import hr.tpopovic.myshowlist.application.domain.model.User;
import hr.tpopovic.myshowlist.application.port.in.RegisterUser;
import hr.tpopovic.myshowlist.application.port.in.RegisterUserResult;
import hr.tpopovic.myshowlist.application.port.out.ForHashingPassword;
import hr.tpopovic.myshowlist.application.port.out.ForSavingUser;
import hr.tpopovic.myshowlist.application.port.out.SavingUserResult;

public class AuthService implements RegisterUser {

    private final ForHashingPassword forHashingPassword;
    private final ForSavingUser forSavingUser;

    public AuthService(ForHashingPassword forHashingPassword, ForSavingUser forSavingUser) {
        this.forHashingPassword = forHashingPassword;
        this.forSavingUser = forSavingUser;
    }

    @Override
    public RegisterUserResult register(RegisteringUser registeringUser) {
        HashedPassword hashedPassword = forHashingPassword.hash(registeringUser.password());
        User user = new User(registeringUser.username(), hashedPassword);
        SavingUserResult result = forSavingUser.save(user);

        return switch (result) {
            case SavingUserResult.Success _ -> new RegisterUserResult.Success();
            case SavingUserResult.Failure _ -> new RegisterUserResult.Failure();
        };
    }

}
