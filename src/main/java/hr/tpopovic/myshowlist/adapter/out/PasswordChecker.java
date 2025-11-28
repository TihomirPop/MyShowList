package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.HashedPassword;
import hr.tpopovic.myshowlist.application.domain.model.Password;
import hr.tpopovic.myshowlist.application.port.out.ForCheckingPassword;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordChecker implements ForCheckingPassword {

    private final PasswordEncoder passwordEncoder;

    public PasswordChecker(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean check(Password password, HashedPassword hashedPassword) {
        return passwordEncoder.matches(
                password.value(),
                hashedPassword.value()
        );
    }

}
