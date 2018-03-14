package no.nav.syfo.security.oidc;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.*;
import org.jose4j.keys.resolvers.VerificationKeyResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OidcTokenValidator {

    private final VerificationKeyResolver verificationKeyResolver;
    private final String expectedIssuer;

    public OidcTokenValidator(VerificationKeyResolver verificationKeyResolver,
                              @Value("${ISSO-ISSUER_URL}") String expectedIssuer) {
        this.verificationKeyResolver = verificationKeyResolver;
        this.expectedIssuer = expectedIssuer;
    }

    public OidcTokenValidatorResult validate(String token) {
        if (token == null) {
            return OidcTokenValidatorResult.invalid("Missing token (token was null)");
        }
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
            .setRequireExpirationTime()
            .setRequireSubject()
            .setExpectedIssuer(expectedIssuer)
            .setSkipDefaultAudienceValidation()
            .setVerificationKeyResolver(verificationKeyResolver)
            .registerValidator(new AzpClaimValidator())
            .build();
        try {
            JwtClaims claims = jwtConsumer.processToClaims(token);
            Optional<String> audience = claims.getAudience().stream().findFirst();
            if (!audience.isPresent()) {
                return OidcTokenValidatorResult.invalid("Fant ingen audience");
            }
            return OidcTokenValidatorResult.valid(claims.getSubject(), audience.get());
        } catch (InvalidJwtException e) {
            return OidcTokenValidatorResult.invalid(e.toString());
        } catch (MalformedClaimException e) {
            return OidcTokenValidatorResult.invalid("Malformed claim: " + e);
        }
    }

    private static class AzpClaimValidator implements Validator {

        @Override
        public String validate(JwtContext jwtContext) throws MalformedClaimException {
            return jwtContext.getJwtClaims().getClaimValue("azp", String.class) == null ? "An azp-claim is required" : null;
        }
    }
}