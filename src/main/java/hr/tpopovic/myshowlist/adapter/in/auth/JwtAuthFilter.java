package hr.tpopovic.myshowlist.adapter.in.auth;

import hr.tpopovic.myshowlist.application.domain.model.Token;
import hr.tpopovic.myshowlist.application.domain.model.Username;
import hr.tpopovic.myshowlist.application.port.in.ValidateToken;
import hr.tpopovic.myshowlist.application.port.in.ValidateTokenResult;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import static java.util.Objects.isNull;

public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final ValidateToken validateToken;

    public JwtAuthFilter(ValidateToken validateToken) {
        this.validateToken = validateToken;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (isNull(authorization) || !authorization.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(TOKEN_PREFIX.length());
        ValidateTokenResult result = validateToken.validate(new Token(token));

        switch (result) {
            case ValidateTokenResult.Valid(Username username) -> setAuthenticationContext(username, request);
            case ValidateTokenResult.Invalid _ -> {
                // noop
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticationContext(Username username, HttpServletRequest request) {
        User principal = new User(username.value(), "", Collections.emptyList());
        var auth = new UsernamePasswordAuthenticationToken(principal, null);
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
