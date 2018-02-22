package no.nav.syfo.config;

import lombok.extern.slf4j.Slf4j;
import no.nav.brukerdialog.security.jwks.JwksKeyHandler;
import no.nav.brukerdialog.security.jwks.JwksKeyHandlerImpl;
import no.nav.brukerdialog.security.oidc.OidcTokenValidator;
import no.nav.sbl.rest.RestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.core.Response;
import java.util.function.Supplier;

import static java.lang.System.setProperty;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static no.nav.brukerdialog.security.Constants.ISSO_EXPECTED_TOKEN_ISSUER;
import static no.nav.sbl.rest.RestUtils.withClient;

@Configuration
@Slf4j
public class OidcTokenValidatorConfig {
    @Bean
    public Supplier<String> jwksStringSupplier(@Value("${ISSO-JWKS_URL}") String issoJwksUrl) {
        return () -> httpGet(issoJwksUrl);
    }

    @Bean
    public JwksKeyHandler jwksKeyHandler(Supplier<String> jwksStringSupplier) {
        return new JwksKeyHandlerImpl(jwksStringSupplier);
    }

    @Bean
    public OidcTokenValidator oidcTokenValidator(@Value("${ISSO-ISSUER_URL}") String issuer, JwksKeyHandler jwksKeyHandler) {
        setProperty(ISSO_EXPECTED_TOKEN_ISSUER, issuer);
        return new OidcTokenValidator(jwksKeyHandler);
    }

    private String httpGet(String issoJwksUrl) {
        if (issoJwksUrl == null) {
            throw new IllegalArgumentException("Missing URL to JWKs location");
        }
        log.info("Starting JWKS update from " + issoJwksUrl);
        return withClient(RestUtils.RestConfig.builder().disableMetrics(true).build(), client -> {
            Response response = client.target(issoJwksUrl)
                    .request()
                    .header(ACCEPT, APPLICATION_JSON)
                    .get();

            int responseStatus = response.getStatus();
            if (responseStatus != 200) {
                String error = "jwks cache update failed : HTTP error code : " + responseStatus;
                log.error(error);
                throw new RuntimeException(error);
            }
            return response.readEntity(String.class);
        });
    }
}


