package hr.tpopovic.myshowlist.adapter.in.auth;

import hr.tpopovic.myshowlist.application.domain.model.Password;
import hr.tpopovic.myshowlist.application.domain.model.RegisteringUser;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import hr.tpopovic.myshowlist.application.port.in.RegisterUser;
import hr.tpopovic.myshowlist.application.port.in.RegisterUserResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${service.api.root-path}")
public class AuthController {

    private final RegisterUser registerUser;

    public AuthController(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    @PostMapping("${service.api.register.path}")
    ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        Username username = new Username(request.username());
        Password password = new Password(request.password());
        RegisteringUser registeringUser = new RegisteringUser(username, password);
        RegisterUserResult result = registerUser.register(registeringUser);

        return switch (result) {
            case RegisterUserResult.Success _ -> ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RegisterResponse.Created());
            case RegisterUserResult.Failure _ -> ResponseEntity.internalServerError()
                    .body(new RegisterResponse.Error("Registration failed"));
        };
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<RegisterResponse> handleValidation(Exception e) {
        return ResponseEntity.badRequest()
                .body(new RegisterResponse.Error(e.getMessage()));
    }
}
