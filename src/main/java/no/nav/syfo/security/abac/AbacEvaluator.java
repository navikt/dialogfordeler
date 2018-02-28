package no.nav.syfo.security.abac;

import lombok.extern.slf4j.Slf4j;
import no.nav.abac.xacml.NavAttributter;
import no.nav.abac.xacml.StandardAttributter;
import no.nav.freg.abac.core.annotation.context.AbacContext;
import no.nav.freg.abac.core.dto.request.XacmlRequest;
import no.nav.freg.abac.core.dto.response.Decision;
import no.nav.freg.abac.core.dto.response.XacmlResponse;
import no.nav.freg.abac.core.service.AbacService;
import org.springframework.stereotype.Component;

import static no.nav.freg.abac.core.dto.response.Decision.DENY;

@Component
@Slf4j
public class AbacEvaluator {
    private static final String PEP_ID_DIALOGFORDELER = "dialogfordeler";
    private static final String DOMENE_SYFO = "syfo";
    private static final String RESOURCE_SYFO_OPPFOELGINGSPLAN = "no.nav.abac.attributter.resource.syfo.dialogmelding";
    private static final String ACTION_SEND = "send";

    private AbacService abacService;
    private AbacContext abacContext;

    public AbacEvaluator(AbacService abacService, AbacContext abacContext) {
        this.abacService = abacService;
        this.abacContext = abacContext;
    }

    public boolean erServicebruker(Object tokenBodyBase64) {
        XacmlRequest request = abacContext.getRequest();

        request.failOnIndeterminate(true);
        request.bias(DENY);

        request.environment(NavAttributter.ENVIRONMENT_FELLES_PEP_ID, PEP_ID_DIALOGFORDELER);
        request.environment(NavAttributter.ENVIRONMENT_FELLES_OIDC_TOKEN_BODY, tokenBodyBase64);

        request.resource(NavAttributter.RESOURCE_FELLES_DOMENE, DOMENE_SYFO);
        request.resource(NavAttributter.RESOURCE_FELLES_RESOURCE_TYPE, RESOURCE_SYFO_OPPFOELGINGSPLAN);

        request.action(StandardAttributter.ACTION_ID, ACTION_SEND);

        try {
            XacmlResponse evaluate = abacService.evaluate(request);
            return evaluate.getDecision() == Decision.PERMIT;
        } catch (RuntimeException e) {
            log.error("Feil ved kall mot ABAC", e);
            throw new RuntimeException("Feil ved kall mot ABAC", e);
        }
    }
}
