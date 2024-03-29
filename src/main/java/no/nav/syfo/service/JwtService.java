package no.nav.syfo.service;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.security.oidc.OidcTokenValidator;
import no.nav.syfo.security.oidc.OidcTokenValidatorResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtService {
    private OidcTokenValidator oidcTokenValidator;

    public JwtService(OidcTokenValidator oidcTokenValidator) {
        this.oidcTokenValidator = oidcTokenValidator;
    }

    public String parseToken(String rawToken) {
        OidcTokenValidatorResult validate = oidcTokenValidator.validate(rawToken);

        if (validate.isValid()) {
            log.debug("JWT is valid");
            return validate.getSubject();
        } else {
            log.error("JWT is invalid");
            throw new BadCredentialsException("JWT is invalid");
        }
    }
}
