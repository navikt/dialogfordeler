//package no.nav.syfo.security.kontroller;
//
//import no.nav.syfo.security.abac.AbacEvaluator;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.security.core.Authentication;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class KontrollerServicebrukerTest {
//    @Mock
//    private AbacEvaluator abacEvaluator;
//    @InjectMocks
//    private KontrollerServicebruker kontrollerServicebruker;
//
//    @Test
//    public void erServicebruker() {
//        Authentication authentication = mock(Authentication.class);
//        String tokenBody = "123";
//        when(authentication.getDetails()).thenReturn(tokenBody);
//        when(abacEvaluator.erServicebruker(tokenBody)).thenReturn(true);
//        assertThat(kontrollerServicebruker.erServicebruker(authentication)).isTrue();
//    }
//
//    @Test
//    public void erIkkeServicebruker() {
//        Authentication authentication = mock(Authentication.class);
//        String tokenBody = "123";
//        when(authentication.getDetails()).thenReturn(tokenBody);
//        when(abacEvaluator.erServicebruker(tokenBody)).thenReturn(false);
//        assertThat(kontrollerServicebruker.erServicebruker(authentication)).isFalse();
//    }
//}