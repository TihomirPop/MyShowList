package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.Role;
import hr.tpopovic.myshowlist.application.domain.model.Token;
import hr.tpopovic.myshowlist.application.domain.model.Username;
 import hr.tpopovic.myshowlist.application.port.out.ForExtractingUserDetailsFromToken;
import hr.tpopovic.myshowlist.application.port.out.ForGeneratingToken;
import hr.tpopovic.myshowlist.application.port.out.ForValidatingToken;
import hr.tpopovic.myshowlist.application.port.out.UserDetailsFromTokenExtractionResult;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

public class JwtTokenManager implements ForGeneratingToken, ForValidatingToken, ForExtractingUserDetailsFromToken {

    private final SecretKey secretKey;
    private final Duration expiration;

    public JwtTokenManager(JwtProperties jwtProperties) {
        secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.base64Secret()));
        expiration = jwtProperties.expiration();
    }

    @Override
    public Token generate(Username username, Role role) {
        String token = Jwts.builder()
                .subject(username.value())
                .claim("role", role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration.toMillis()))
                .signWith(secretKey)
                .compact();

        return new Token(token);
    }

    @Override
    public boolean validate(Token token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token.value());
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public UserDetailsFromTokenExtractionResult extract(Token token) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token.value())
                    .getPayload();

            String username = claims.getSubject();
            String roleStr = claims.get("role", String.class);
            Role role = Role.valueOf(roleStr);

            return new UserDetailsFromTokenExtractionResult.Success(
                new Username(username),
                role
            );
        } catch (JwtException | IllegalArgumentException e) {
            return new UserDetailsFromTokenExtractionResult.Failure();
        }
    }

}
