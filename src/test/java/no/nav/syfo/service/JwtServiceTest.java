package no.nav.syfo.service;

import no.nav.brukerdialog.security.oidc.OidcTokenValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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
        when(oidcTokenValidator.validate("Token")).thenReturn(OidcTokenValidator.OidcTokenValidatorResult.valid("Subject", 0));

        assertThat(jwtService.parseToken("Token")).isEqualTo("Subject");
    }

    @Test(expected = BadCredentialsException.class)
    public void parseTokenInvalidToken() {
        when(oidcTokenValidator.validate("Token")).thenReturn(OidcTokenValidator.OidcTokenValidatorResult.invalid("Token expired"));

        jwtService.parseToken("Token");
    }
}