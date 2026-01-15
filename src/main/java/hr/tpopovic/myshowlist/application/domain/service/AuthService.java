package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.*;
import hr.tpopovic.myshowlist.application.port.out.*;

public class AuthService implements RegisterUser, LoginUser, ValidateToken {

    private final ForHashingPassword forHashingPassword;
    private final ForSavingUser forSavingUser;
    private final ForFetchingPasswordHash forFetchingPasswordHash;
    private final ForFetchingUserWithRole forFetchingUserWithRole;
    private final ForCheckingPassword forCheckingPassword;
    private final ForGeneratingToken forGeneratingToken;
    private final ForValidatingToken forValidatingToken;
    private final ForExtractingUserDetailsFromToken forExtractingUserDetailsFromToken;

    public AuthService(
            ForHashingPassword forHashingPassword,
            ForSavingUser forSavingUser,
            ForFetchingPasswordHash forFetchingPasswordHash,
            ForFetchingUserWithRole forFetchingUserWithRole,
            ForCheckingPassword forCheckingPassword,
            ForGeneratingToken forGeneratingToken,
            ForValidatingToken forValidatingToken,
            ForExtractingUserDetailsFromToken forExtractingUserDetailsFromToken
    ) {
        this.forHashingPassword = forHashingPassword;
        this.forSavingUser = forSavingUser;
        this.forFetchingPasswordHash = forFetchingPasswordHash;
        this.forFetchingUserWithRole = forFetchingUserWithRole;
        this.forCheckingPassword = forCheckingPassword;
        this.forGeneratingToken = forGeneratingToken;
        this.forValidatingToken = forValidatingToken;
        this.forExtractingUserDetailsFromToken = forExtractingUserDetailsFromToken;
    }

    @Override
    public RegisterUserResult register(RegisterCommand command) {
        HashedPassword hashedPassword = forHashingPassword.hash(command.password());
        User user = new User(command.username(), hashedPassword, Role.USER);
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
        FetchUserWithRoleResult fetchRoleResult = forFetchingUserWithRole.fetch(username);

        return switch (fetchPasswordHashResult) {
            case FetchPasswordHashResult.Success(HashedPassword hashedPassword) -> switch (fetchRoleResult) {
                case FetchUserWithRoleResult.Success(UserId _, Role role) ->
                    checkPasswordAndGenerateToken(username, password, hashedPassword, role);
                case FetchUserWithRoleResult.UserNotFound _ -> new LoginResult.WrongCredentials();
                case FetchUserWithRoleResult.Failure _ -> new LoginResult.Failure();
            };
            case FetchPasswordHashResult.NotFound _ -> new LoginResult.WrongCredentials();
            case FetchPasswordHashResult.Failure _ -> new LoginResult.Failure();
        };
    }

    @Override
    public ValidateTokenResult validate(Token token) {
        boolean isValid = forValidatingToken.validate(token);
        if (!isValid) {
            return new ValidateTokenResult.Invalid();
        }

        UserDetailsFromTokenExtractionResult result = forExtractingUserDetailsFromToken.extract(token);

        return switch (result) {
            case UserDetailsFromTokenExtractionResult.Success(Username username, Role role) ->
                new ValidateTokenResult.Valid(username, role);
            case UserDetailsFromTokenExtractionResult.Failure _ -> new ValidateTokenResult.Failure();
        };
    }

    private LoginResult checkPasswordAndGenerateToken(
            Username username,
            Password password,
            HashedPassword hashedPassword,
            Role role
    ) {
        boolean passwordMatches = forCheckingPassword.check(password, hashedPassword);

        if (!passwordMatches) {
            return new LoginResult.WrongCredentials();
        }

        Token token = forGeneratingToken.generate(username, role);

        return new LoginResult.Success(token);
    }

}
