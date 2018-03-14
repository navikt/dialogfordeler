package no.nav.syfo.security.oidc;

public class OidcTokenValidatorResult {
    private final String subject;
    private final String audience;
    private final boolean isValid;
    private final String errorMessage;

    private OidcTokenValidatorResult(String subject, String audience, boolean isValid, String errorMessage) {
        this.audience = audience;
        this.isValid = isValid;
        this.errorMessage = errorMessage;
        this.subject = subject;
    }

    public static OidcTokenValidatorResult invalid(String errorMessage) {
        return new OidcTokenValidatorResult(null, null, false, errorMessage);
    }

    public static OidcTokenValidatorResult valid(String subject, String audience) {
        return new OidcTokenValidatorResult(subject, audience, true, null);
    }

    public boolean isValid() {
        return isValid;
    }

    public String getSubject() {
        return subject;
    }

    public String getAudience() {
        return audience;
    }

    public String getErrorMessage() {
        if (isValid) {
            throw new IllegalArgumentException("Can't get error message from valid token");
        }
        return errorMessage;
    }
}