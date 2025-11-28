package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.*;
import hr.tpopovic.myshowlist.application.port.out.*;

public class AuthService implements RegisterUser, LoginUser {

    private final ForHashingPassword forHashingPassword;
    private final ForSavingUser forSavingUser;
    private final ForFetchingPasswordHash forFetchingPasswordHash;
    private final ForCheckingPassword forCheckingPassword;
    private final ForGeneratingToken forGeneratingToken;

    public AuthService(
            ForHashingPassword forHashingPassword,
            ForSavingUser forSavingUser,
            ForFetchingPasswordHash forFetchingPasswordHash,
            ForCheckingPassword forCheckingPassword,
            ForGeneratingToken forGeneratingToken
    ) {
        this.forHashingPassword = forHashingPassword;
        this.forSavingUser = forSavingUser;
        this.forFetchingPasswordHash = forFetchingPasswordHash;
        this.forCheckingPassword = forCheckingPassword;
        this.forGeneratingToken = forGeneratingToken;
    }

    @Override
    public RegisterUserResult register(RegisterCommand command) {
        HashedPassword hashedPassword = forHashingPassword.hash(command.password());
        User user = new User(command.username(), hashedPassword);
        SavingUserResult result = forSavingUser.save(user);

        return switch (result) {
            case SavingUserResult.Success _ -> new RegisterUserResult.Success();
            case SavingUserResult.Failure _ -> new RegisterUserResult.Failure();
        };
    }

    @Override
    public LoginResult login(LoginCommand command) {
        Username username = command.username();
        Password password = command.password();
        FetchPasswordHashResult fetchPasswordHashResult = forFetchingPasswordHash.fetch(username);

        return switch (fetchPasswordHashResult) {
            case FetchPasswordHashResult.Success(HashedPassword hashedPassword) -> checkPasswordAndGenerateToken(username, password, hashedPassword);
            case FetchPasswordHashResult.NotFound _ -> new LoginResult.WrongCredentials();
            case FetchPasswordHashResult.Failure _ -> new LoginResult.Failure();
        };
    }

    private LoginResult checkPasswordAndGenerateToken(
            Username username,
            Password password,
            HashedPassword hashedPassword
    ) {
        boolean passwordMatches = forCheckingPassword.check(password, hashedPassword);

        if (!passwordMatches) {
            return new LoginResult.WrongCredentials();
        }

        Token token = forGeneratingToken.generate(username);

        return new LoginResult.Success(token);
    }

}
