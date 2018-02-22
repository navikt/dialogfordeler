package no.nav.syfo.provider.rest;

import no.nav.syfo.config.OidcTokenValidatorConfig;
import no.nav.syfo.security.MethodSecurityConfig;
import no.nav.syfo.security.WebSecurityConfig;
import no.nav.syfo.security.expression.CustomMethodExpressionHandler;
import no.nav.syfo.security.jwt.JwtAuthenticationProvider;
import no.nav.syfo.service.JwtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        ApiController.class,
        WebSecurityConfig.class,
        MethodSecurityConfig.class,
        CustomMethodExpressionHandler.class,
        JwtAuthenticationProvider.class,
        JwtService.class,
        OidcTokenValidatorConfig.class
})
public class ApiControllerSecurityTest {
    @Inject
    private ApiController apiController;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void apiUnauthenticated() throws Exception {
        apiController.api();
    }

    @Test
    @WithMockUser(username = "srvTest")
    public void apiAuthenticated() throws Exception {
        apiController.api();
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "Z000000")
    public void apiAuthenticatedWrongUser() throws Exception {
        apiController.api();
    }
}