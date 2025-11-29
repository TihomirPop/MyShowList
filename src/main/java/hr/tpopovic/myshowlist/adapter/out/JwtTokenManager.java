package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.Token;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import hr.tpopovic.myshowlist.application.port.out.ForExtractingUsernameFromToken;
import hr.tpopovic.myshowlist.application.port.out.ForGeneratingToken;
import hr.tpopovic.myshowlist.application.port.out.ForValidatingToken;
import hr.tpopovic.myshowlist.application.port.out.UsernameFromTokenExtractionResult;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

public class JwtTokenManager implements ForGeneratingToken, ForValidatingToken, ForExtractingUsernameFromToken {

    private final SecretKey secretKey;
    private final Duration expiration;

    public JwtTokenManager(JwtProperties jwtProperties) {
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
    public UsernameFromTokenExtractionResult extract(Token token) {
        try {
            String username = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token.value())
                    .getPayload()
                    .getSubject();

            return new UsernameFromTokenExtractionResult.Success(new Username(username));
        } catch (JwtException | IllegalArgumentException e) {
            return new UsernameFromTokenExtractionResult.Failure();
        }
    }

}
