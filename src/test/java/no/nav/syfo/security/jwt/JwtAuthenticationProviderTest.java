package no.nav.syfo.security.jwt;

import no.nav.syfo.service.JwtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationProviderTest {
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private JwtAuthenticationProvider provider;

    @Test
    public void supportsJwtToken() {
        assertThat(provider.supports(JwtUsernamePasswordAuthenticationToken.class)).isTrue();
    }

    @Test
    public void supportsIkkeAndreToken() {
        assertThat(provider.supports(UsernamePasswordAuthenticationToken.class)).isFalse();
    }

    @Test
    public void noAdditionalAuthenticationChecks() {
        UserDetails userDetails = mock(UserDetails.class);
        UsernamePasswordAuthenticationToken token = mock(UsernamePasswordAuthenticationToken.class);

        provider.additionalAuthenticationChecks(userDetails, token);

        verifyZeroInteractions(userDetails);
        verifyZeroInteractions(token);
    }

    @Test
    public void retrieveUser() {
        UsernamePasswordAuthenticationToken token = mock(UsernamePasswordAuthenticationToken.class);
        when(token.getCredentials()).thenReturn("Token");
        when(jwtService.parseToken("Token")).thenReturn("Subject");

        UserDetails user = provider.retrieveUser("Username", token);

        assertThat(user.getUsername()).isEqualTo("Subject");
    }

    @Test(expected = BadCredentialsException.class)
    public void retrieveUserTokenInvalid() {
        UsernamePasswordAuthenticationToken token = mock(UsernamePasswordAuthenticationToken.class);
        when(token.getCredentials()).thenReturn("Token");
        when(jwtService.parseToken("Token")).thenThrow(new BadCredentialsException("Bad credentials"));

        provider.retrieveUser("Username", token);
    }
}