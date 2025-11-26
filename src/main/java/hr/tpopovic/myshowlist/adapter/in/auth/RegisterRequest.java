package hr.tpopovic.myshowlist.adapter.in.auth;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password
) {

}
