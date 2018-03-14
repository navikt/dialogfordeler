//package no.nav.syfo.security.kontroller;
//
//import no.nav.syfo.security.abac.AbacEvaluator;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KontrollerServicebruker {
//    private AbacEvaluator abacEvaluator;
//
//    public KontrollerServicebruker(AbacEvaluator abacEvaluator) {
//        this.abacEvaluator = abacEvaluator;
//    }
//
//    public boolean erServicebruker(Authentication authentication) {
//        return abacEvaluator.erServicebruker(authentication.getDetails());
//    }
//}
