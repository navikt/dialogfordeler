package no.nav.syfo.domain;

import no.kith.xmlstds.dialog._2013_01_23.XMLDialogmelding;
import no.kith.xmlstds.dialog._2013_01_23.XMLForesporsel;
import no.kith.xmlstds.dialog._2013_01_23.XMLNotat;
import no.nav.syfo.domain.hodemeldingwrapper.Dialogmelding;
import no.nav.syfo.domain.hodemeldingwrapper.Dialogmelding1_1;
import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class Dialogmelding1_1Test {

    @Test
    public void erForesporsel() {
        Dialogmelding dialogmelding = new Dialogmelding1_1(new XMLDialogmelding().withForesporsel(new XMLForesporsel()));
        assertThat(dialogmelding.erForesporsel()).isTrue();
    }

    @Test
    public void erNotat() {
        Dialogmelding dialogmelding = new Dialogmelding1_1(new XMLDialogmelding().withNotat(new XMLNotat()));
        assertThat(dialogmelding.erNotat()).isTrue();
    }

    @Test
    public void getDokIdNotatStream() {
        Dialogmelding dialogmelding = new Dialogmelding1_1(new XMLDialogmelding().withNotat(new XMLNotat().withDokIdNotat("DokIdNotat")));
        assertThat(dialogmelding.getDokIdNotatStream().collect(toList())).hasSize(1).contains("DokIdNotat");
    }
}