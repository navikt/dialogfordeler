package no.nav.syfo.web.rest.dialogmelding;

import no.nav.syfo.config.OidcTokenValidatorConfig;
import no.nav.syfo.security.MethodSecurityConfig;
import no.nav.syfo.security.WebSecurityConfig;
import no.nav.syfo.security.expression.CustomMethodExpressionHandler;
import no.nav.syfo.security.jwt.JwtAuthenticationProvider;
import no.nav.syfo.service.JwtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        DialogmeldingController.class,
        WebSecurityConfig.class,
        MethodSecurityConfig.class,
        CustomMethodExpressionHandler.class,
        JwtAuthenticationProvider.class,
        JwtService.class,
        OidcTokenValidatorConfig.class
})
public class DialogmeldingControllerSecurityTest {
    @MockBean
    private DialogmeldingService dialogmeldingService;
    @Inject
    private DialogmeldingController dialogmeldingController;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void apiUnauthenticated() throws Exception {
        SecurityContextHolder.clearContext();
        dialogmeldingController.opprettDialogmelding(null);
    }

    @Test
    @WithMockUser(username = "srvTest")
    public void apiAuthenticated() throws Exception {
        dialogmeldingController.opprettDialogmelding(null);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "Z000000")
    public void apiAuthenticatedWrongUser() throws Exception {
        dialogmeldingController.opprettDialogmelding(null);
    }
}