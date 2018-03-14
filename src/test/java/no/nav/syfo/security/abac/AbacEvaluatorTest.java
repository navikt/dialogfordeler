//package no.nav.syfo.security.abac;
//
//import no.nav.freg.abac.core.annotation.context.AbacContext;
//import no.nav.freg.abac.core.dto.request.XacmlAttribute;
//import no.nav.freg.abac.core.dto.request.XacmlRequest;
//import no.nav.freg.abac.core.dto.response.Decision;
//import no.nav.freg.abac.core.dto.response.XacmlResponse;
//import no.nav.freg.abac.core.service.AbacService;
//import org.assertj.core.api.SoftAssertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import static no.nav.abac.xacml.NavAttributter.*;
//import static no.nav.abac.xacml.StandardAttributter.ACTION_ID;
//import static no.nav.freg.abac.core.dto.response.Decision.DENY;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AbacEvaluatorTest {
//    @Mock
//    private AbacService abacService;
//    @Mock
//    private AbacContext abacContext;
//    @InjectMocks
//    private AbacEvaluator abacEvaluator;
//
//    @Test
//    public void erServicebruker() {
//        XacmlRequest request = new XacmlRequest();
//        XacmlResponse response = mock(XacmlResponse.class);
//        when(abacContext.getRequest()).thenReturn(request);
//        when(abacService.evaluate(any())).thenReturn(response);
//        when(response.getDecision()).thenReturn(Decision.PERMIT);
//
//        SoftAssertions assertions = new SoftAssertions();
//        assertions.assertThat(abacEvaluator.erServicebruker("token")).isTrue();
//        assertions.assertThat(request.isFailOnIndeterminate()).isTrue();
//        assertions.assertThat(request.getBias()).isSameAs(DENY);
//        assertions.assertThat(request.getEnvironments())
//                .containsExactlyInAnyOrder(
//                        new XacmlAttribute(ENVIRONMENT_FELLES_PEP_ID, "dialogfordeler"),
//                        new XacmlAttribute(ENVIRONMENT_FELLES_OIDC_TOKEN_BODY, "token"));
//        assertions.assertThat(request.getResources())
//                .containsExactlyInAnyOrder(
//                        new XacmlAttribute(RESOURCE_FELLES_DOMENE, "syfo"),
//                        new XacmlAttribute(RESOURCE_FELLES_RESOURCE_TYPE, "no.nav.abac.attributter.resource.syfo.dialogmelding"));
//        assertions.assertThat(request.getActions())
//                .containsExactlyInAnyOrder(
//                        new XacmlAttribute(ACTION_ID, "send"));
//        assertions.assertAll();
//    }
//
//    @Test
//    public void erIkkeServicebruker() {
//        XacmlRequest request = new XacmlRequest();
//        XacmlResponse response = mock(XacmlResponse.class);
//        when(abacContext.getRequest()).thenReturn(request);
//        when(abacService.evaluate(any())).thenReturn(response);
//        when(response.getDecision()).thenReturn(Decision.DENY);
//
//        SoftAssertions assertions = new SoftAssertions();
//        assertions.assertThat(abacEvaluator.erServicebruker("token")).isFalse();
//        assertions.assertThat(request.isFailOnIndeterminate()).isTrue();
//        assertions.assertThat(request.getBias()).isSameAs(DENY);
//        assertions.assertThat(request.getEnvironments())
//                .containsExactlyInAnyOrder(
//                        new XacmlAttribute(ENVIRONMENT_FELLES_PEP_ID, "dialogfordeler"),
//                        new XacmlAttribute(ENVIRONMENT_FELLES_OIDC_TOKEN_BODY, "token"));
//        assertions.assertThat(request.getResources())
//                .containsExactlyInAnyOrder(
//                        new XacmlAttribute(RESOURCE_FELLES_DOMENE, "syfo"),
//                        new XacmlAttribute(RESOURCE_FELLES_RESOURCE_TYPE, "no.nav.abac.attributter.resource.syfo.dialogmelding"));
//        assertions.assertThat(request.getActions())
//                .containsExactlyInAnyOrder(
//                        new XacmlAttribute(ACTION_ID, "send"));
//        assertions.assertAll();
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void erServicebrukerFeilerMotAbac() {
//        XacmlRequest request = new XacmlRequest();
//        when(abacContext.getRequest()).thenReturn(request);
//        when(abacService.evaluate(any())).thenThrow(new RuntimeException());
//
//        abacEvaluator.erServicebruker("token");
//    }
//}