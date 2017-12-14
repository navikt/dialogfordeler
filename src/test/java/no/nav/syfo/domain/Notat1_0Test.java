package no.nav.syfo.domain;

import no.kith.xmlstds.dialog._2006_10_11.XMLNotat;
import no.nav.syfo.domain.hodemeldingwrapper.Notat;
import no.nav.syfo.domain.hodemeldingwrapper.Notat1_0;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Notat1_0Test {
    @Test
    public void getDokIdNotat() {
        Notat notat = new Notat1_0(new XMLNotat().withDokIdNotat("DokIdNotat"));
        assertThat(notat.getDokIdNotat()).isEqualTo("DokIdNotat");
    }
}