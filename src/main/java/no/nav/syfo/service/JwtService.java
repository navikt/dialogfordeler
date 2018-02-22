package no.nav.syfo.service;

import lombok.extern.slf4j.Slf4j;
import no.nav.brukerdialog.security.jwks.JwksKeyHandlerImpl;
import no.nav.brukerdialog.security.oidc.OidcTokenValidator;
import no.nav.sbl.rest.RestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

import static java.lang.System.setProperty;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static no.nav.brukerdialog.security.Constants.ISSO_EXPECTED_TOKEN_ISSUER;
import static no.nav.sbl.rest.RestUtils.withClient;

@Service
@Slf4j
public class JwtService {
    private final String issoJwksUrl;

    public JwtService(@Value("${ISSO-ISSUER_URL}") String issuer, @Value("${ISSO-JWKS_URL}") String issoJwksUrl) {
        setProperty(ISSO_EXPECTED_TOKEN_ISSUER, issuer);
        this.issoJwksUrl = issoJwksUrl;
    }

    public String parseToken(String rawToken) {
        OidcTokenValidator validator = new OidcTokenValidator(new JwksKeyHandlerImpl(this::httpGet));

        OidcTokenValidator.OidcTokenValidatorResult validate = validator.validate(rawToken);

        if (validate.isValid()) {
            log.debug("JWT is valid");
            return validate.getSubject();
        } else {
            log.error("JWT is invalid");
            throw new BadCredentialsException("JWT is invalid");
        }
    }

    private String httpGet() {
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
