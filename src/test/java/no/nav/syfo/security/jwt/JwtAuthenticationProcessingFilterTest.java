package no.nav.syfo.security.jwt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationProcessingFilterTest {
    @Mock
    private AuthenticationManager authenticationManager;
    private JwtAuthenticationProcessingFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new JwtAuthenticationProcessingFilter(AnyRequestMatcher.INSTANCE);
        filter.setAuthenticationManager(authenticationManager);
    }

    @Test
    public void attemptAuthenticationRemoveBearerPrefix() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer TokenHeader.TokenBody");

        filter.attemptAuthentication(request, null);

        ArgumentCaptor<JwtUsernamePasswordAuthenticationToken> captor = ArgumentCaptor.forClass(JwtUsernamePasswordAuthenticationToken.class);
        verify(authenticationManager).authenticate(captor.capture());

        assertThat(captor.getValue().getCredentials()).isEqualTo("TokenHeader.TokenBody");
    }

    @Test(expected = BadCredentialsException.class)
    public void attemptAuthenticationMissingAuthorizationHeader() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);

        filter.attemptAuthentication(request, null);
    }

    @Test(expected = BadCredentialsException.class)
    public void attemptAuthenticationMissingBearerPrefix() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("TokenHeader.TokenBody");

        filter.attemptAuthentication(request, null);
    }

    @Test(expected = BadCredentialsException.class)
    public void attemptAuthenticationMissingTokenBody() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer TokenHeader");

        filter.attemptAuthentication(request, null);
    }

    @Test
    public void successfulAuthentication() throws Exception {
        FilterChain chain = mock(FilterChain.class);
        Authentication authResult = mock(Authentication.class);

        filter.successfulAuthentication(null, null, chain, authResult);

        verify(chain).doFilter(null, null);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isSameAs(authResult);
    }
}