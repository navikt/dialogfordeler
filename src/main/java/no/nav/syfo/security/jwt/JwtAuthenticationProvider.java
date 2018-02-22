package no.nav.syfo.security.jwt;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;

@Component
@Slf4j
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Inject
    private JwtService jwtService;

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtUsernamePasswordAuthenticationToken.class.equals(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String rawToken = (String) authentication.getCredentials();

        String subject = jwtService.parseToken(rawToken);

        return new User(subject, rawToken, Collections.emptyList());
    }
}
