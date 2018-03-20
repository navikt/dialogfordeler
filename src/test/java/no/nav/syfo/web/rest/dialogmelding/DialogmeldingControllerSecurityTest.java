package no.nav.syfo.web.rest.dialogmelding;

import no.nav.syfo.config.OidcTokenValidatorConfig;
import no.nav.syfo.security.WebSecurityConfig;
import no.nav.syfo.security.jwt.JwtAuthenticationProvider;
import no.nav.syfo.security.kontroller.KontrollerServicebruker;
import no.nav.syfo.security.oidc.OidcTokenValidator;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        DialogmeldingController.class,
        WebSecurityConfig.class,
        KontrollerServicebruker.class,
        JwtAuthenticationProvider.class,
        JwtService.class,
        OidcTokenValidator.class,
        OidcTokenValidatorConfig.class
})
public class DialogmeldingControllerSecurityTest {
    @MockBean
    private DialogmeldingService dialogmeldingService;
    @MockBean
    private KontrollerServicebruker kontrollerServicebruker;
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
        when(kontrollerServicebruker.erServicebruker(any())).thenReturn(true);
        dialogmeldingController.opprettDialogmelding(null);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "Z000000")
    public void apiAuthenticatedWrongUser() throws Exception {
        dialogmeldingController.opprettDialogmelding(null);
    }
}