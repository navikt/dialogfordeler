package no.nav.syfo.domain;

import no.kith.xmlstds.dialog._2013_01_23.XMLNotat;
import no.nav.syfo.domain.hodemeldingwrapper.Notat;
import no.nav.syfo.domain.hodemeldingwrapper.Notat1_1;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Notat1_1Test {
    @Test
    public void getDokIdNotat() {
        Notat notat = new Notat1_1(new XMLNotat().withDokIdNotat("DokIdNotat"));
        assertThat(notat.getDokIdNotat()).isEqualTo("DokIdNotat");
    }
}