package no.nav.syfo.domain;

import no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding;
import no.kith.xmlstds.dialog._2006_10_11.XMLForesporsel;
import no.kith.xmlstds.dialog._2006_10_11.XMLNotat;
import no.nav.syfo.domain.hodemeldingwrapper.Dialogmelding;
import no.nav.syfo.domain.hodemeldingwrapper.Dialogmelding1_0;
import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class Dialogmelding1_0Test {

    @Test
    public void erForesporsel() {
        Dialogmelding dialogmelding = new Dialogmelding1_0(new XMLDialogmelding().withForesporsel(new XMLForesporsel()));
        assertThat(dialogmelding.erForesporsel()).isTrue();
    }

    @Test
    public void erNotat() {
        Dialogmelding dialogmelding = new Dialogmelding1_0(new XMLDialogmelding().withNotat(new XMLNotat()));
        assertThat(dialogmelding.erNotat()).isTrue();
    }

    @Test
    public void getDokIdNotatStream() {
        Dialogmelding dialogmelding = new Dialogmelding1_0(new XMLDialogmelding().withNotat(new XMLNotat().withDokIdNotat("DokIdNotat")));
        assertThat(dialogmelding.getDokIdNotatStream().collect(toList())).hasSize(1).contains("DokIdNotat");
    }
}