package no.nav.syfo.config;

import no.nav.brukerdialog.security.jwks.JwksKeyHandler;
import no.nav.brukerdialog.security.oidc.OidcTokenValidator;
import no.nav.sbl.rest.RestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.function.Function;

import static no.nav.brukerdialog.security.Constants.ISSO_EXPECTED_TOKEN_ISSUER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RestUtils.class)
public class OidcTokenValidatorConfigTest {
    private OidcTokenValidatorConfig oidcTokenValidatorConfig;

    @Before
    public void setUp() throws Exception {
        mockStatic(RestUtils.class);
        when(RestUtils.withClient(any(), any())).thenReturn("Noe");

        oidcTokenValidatorConfig = new OidcTokenValidatorConfig();
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingURLToJWKsLocation() {
        oidcTokenValidatorConfig.jwksStringSupplier(null).get();
    }

    @Test
    public void testClientFunction() {
        Client client = mock(Client.class);
        WebTarget target = mock(WebTarget.class);
        Invocation.Builder builder = mock(Invocation.Builder.class);
        Response response = mock(Response.class);
        when(client.target(anyString())).thenReturn(target);
        when(target.request()).thenReturn(builder);
        when(builder.header(anyString(), anyString())).thenReturn(builder);
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(200);
        when(response.readEntity(any(Class.class))).thenReturn("Response");

        assertThat(oidcTokenValidatorConfig.jwksStringSupplier("isso").get()).isEqualTo("Noe");

        ArgumentCaptor<Function> captor = ArgumentCaptor.forClass(Function.class);
        verifyStatic();
        RestUtils.withClient(any(), captor.capture());

        Function<Client, String> clientFunction = captor.getValue();

        String entity = clientFunction.apply(client);

        assertThat(entity).isEqualTo("Response");
    }

    @Test(expected = RuntimeException.class)
    public void testClientFunctionStatusNot200() {
        Client client = mock(Client.class);
        WebTarget target = mock(WebTarget.class);
        Invocation.Builder builder = mock(Invocation.Builder.class);
        Response response = mock(Response.class);
        when(client.target(anyString())).thenReturn(target);
        when(target.request()).thenReturn(builder);
        when(builder.header(anyString(), anyString())).thenReturn(builder);
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(404);

        assertThat(oidcTokenValidatorConfig.jwksStringSupplier("isso").get()).isEqualTo("Noe");

        ArgumentCaptor<Function> captor = ArgumentCaptor.forClass(Function.class);
        verifyStatic();
        RestUtils.withClient(any(), captor.capture());

        Function<Client, String> clientFunction = captor.getValue();

        clientFunction.apply(client);
    }

    @Test
    public void jwksKeyHandler() {
        assertThat(oidcTokenValidatorConfig.jwksKeyHandler(() -> "")).isInstanceOf(JwksKeyHandler.class);
    }

    @Test
    public void oidcTokenValidator() {
        assertThat(oidcTokenValidatorConfig.oidcTokenValidator("Issuer", null)).isInstanceOf(OidcTokenValidator.class);
        assertThat(System.getProperty(ISSO_EXPECTED_TOKEN_ISSUER)).isEqualTo("Issuer");
    }
}