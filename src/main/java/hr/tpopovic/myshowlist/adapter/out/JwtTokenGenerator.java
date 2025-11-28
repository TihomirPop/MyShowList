package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.Token;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import hr.tpopovic.myshowlist.application.port.out.ForGeneratingToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

public class JwtTokenGenerator implements ForGeneratingToken {

    private final SecretKey secretKey;
    private final Duration expiration;

    public JwtTokenGenerator(JwtProperties jwtProperties) {
        secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.base64Secret()));
        expiration = jwtProperties.expiration();
    }

    @Override
    public Token generate(Username username) {
        String token = Jwts.builder()
                .subject(username.value())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration.toMillis()))
                .signWith(secretKey)
                .compact();

        return new Token(token);
    }

}
