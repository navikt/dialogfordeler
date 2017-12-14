package no.nav.syfo.domain;

import no.kith.xmlstds.dialog._2006_10_11.XMLForesporsel;
import no.nav.syfo.domain.hodemeldingwrapper.Foresporsel1_0;
import org.junit.Test;

public class Foresporsel1_0Test {
    @Test
    public void getXmlForesporsel() {
        new Foresporsel1_0(new XMLForesporsel());
    }
}