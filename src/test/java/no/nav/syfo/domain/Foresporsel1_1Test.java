package no.nav.syfo.domain;

import no.kith.xmlstds.dialog._2013_01_23.XMLForesporsel;
import no.nav.syfo.domain.hodemeldingwrapper.Foresporsel1_1;
import org.junit.Test;

public class Foresporsel1_1Test {
    @Test
    public void getXmlForesporsel() {
        new Foresporsel1_1(new XMLForesporsel());
    }
}