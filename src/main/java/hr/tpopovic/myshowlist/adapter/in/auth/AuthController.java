package hr.tpopovic.myshowlist.adapter.in.auth;

import hr.tpopovic.myshowlist.application.port.in.RegisterUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service.api.root-path}")
public class AuthController {

    private final RegisterUser registerUser;

    public AuthController(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    @PostMapping("${service.api.register.path}")
    ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.internalServerError().build();
    }

}
