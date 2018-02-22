package no.nav.syfo.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public JwtUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
