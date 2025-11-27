package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.HashedPassword;
import hr.tpopovic.myshowlist.application.domain.model.Password;
import hr.tpopovic.myshowlist.application.port.out.ForHashingPassword;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHasher implements ForHashingPassword {

    private final PasswordEncoder passwordEncoder;

    public PasswordHasher(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public HashedPassword hash(Password password) {
        String hashedPassword = passwordEncoder.encode(password.value());
        return new HashedPassword(hashedPassword);
    }

}
