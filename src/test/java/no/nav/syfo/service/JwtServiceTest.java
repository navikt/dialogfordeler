package no.nav.syfo.service;

import no.nav.syfo.security.oidc.OidcTokenValidator;
import no.nav.syfo.security.oidc.OidcTokenValidatorResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtServiceTest {
    @Mock
    private OidcTokenValidator oidcTokenValidator;
    @InjectMocks
    private JwtService jwtService;

    @Test
    public void parseTokenValidToken() {
        when(oidcTokenValidator.validate("Token")).thenReturn(OidcTokenValidatorResult.valid("Subject", "aud"));

        assertThat(jwtService.parseToken("Token")).isEqualTo("Subject");
    }

    @Test(expected = BadCredentialsException.class)
    public void parseTokenInvalidToken() {
        when(oidcTokenValidator.validate("Token")).thenReturn(OidcTokenValidatorResult.invalid("Token expired"));

        jwtService.parseToken("Token");
    }
}