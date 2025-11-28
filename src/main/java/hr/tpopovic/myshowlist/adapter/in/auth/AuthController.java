package hr.tpopovic.myshowlist.adapter.in.auth;

import hr.tpopovic.myshowlist.application.port.in.*;
import hr.tpopovic.myshowlist.application.domain.model.Password;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${service.api.root-path}")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final RegisterUser registerUser;
    private final LoginUser loginUser;

    public AuthController(RegisterUser registerUser, LoginUser loginUser) {
        this.registerUser = registerUser;
        this.loginUser = loginUser;
    }

    @PostMapping("${service.api.register.path}")
    ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        Username username = new Username(request.username());
        Password password = new Password(request.password());
        RegisterCommand command = new RegisterCommand(username, password);
        RegisterUserResult result = registerUser.register(command);

        return switch (result) {
            case RegisterUserResult.Success _ -> ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RegisterResponse.Created());
            case RegisterUserResult.Failure _ -> ResponseEntity.internalServerError()
                    .body(new RegisterResponse.Error("Registration failed"));
        };
    }

    @PostMapping("${service.api.login.path}")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Username username = new Username(request.username());
        Password password = new Password(request.password());
        LoginCommand loginCommand = new LoginCommand(username, password);
        LoginResult result = loginUser.login(loginCommand);

        return switch (result) {
            case LoginResult.Success success -> ResponseEntity.ok(new LoginResponse.Ok(success.token().value()));
            case LoginResult.WrongCredentials wrongCredentials -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse.Error("Wrong username or password"));
            case LoginResult.Failure failure -> ResponseEntity.internalServerError()
                    .body(new LoginResponse.Error("Login failed"));
        };
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<RegisterResponse> handleValidation(Exception e) {
        return ResponseEntity.badRequest()
                .body(new RegisterResponse.Error(e.getMessage()));
    }
}
