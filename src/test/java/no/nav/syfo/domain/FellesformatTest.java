package no.nav.syfo.domain;

import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.util.JAXB;
import no.trygdeetaten.xml.eiff._1.XMLEIFellesformat;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static no.nav.syfo.testdata.FellesformatXml.fellesformat;
import static no.nav.syfo.testdata.HodemeldingXml.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FellesformatTest {

    @Test
    public void erForesporsel() throws Exception {
        String message = "Message";
        String melding = fellesformat(hodemelding(DIALOG0));
        XMLEIFellesformat xmlFellesformat = JAXB.unmarshalMelding(melding);
        Fellesformat fellesformat = new Fellesformat(message, xmlFellesformat);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(fellesformat.getMessage()).isEqualTo(message);
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erForesporsel()).isTrue());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erNotat()).isFalse());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.harVedlegg()).isFalse());
        assertions.assertAll();
    }

    @Test
    public void erNotat() throws Exception {
        String message = "Message";
        String melding = fellesformat(hodemelding(NOTAT1));
        XMLEIFellesformat xmlFellesformat = JAXB.unmarshalMelding(melding);
        Fellesformat fellesformat = new Fellesformat(message, xmlFellesformat);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(fellesformat.getMessage()).isEqualTo(message);
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erForesporsel()).isFalse());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erNotat()).isTrue());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.harVedlegg()).isFalse());
        assertions.assertAll();
    }

    @Test
    public void harVedlegg() throws Exception {
        String message = "Message";
        String melding = fellesformat(hodemelding(VEDLEGG));
        XMLEIFellesformat xmlFellesformat = JAXB.unmarshalMelding(melding);
        Fellesformat fellesformat = new Fellesformat(message, xmlFellesformat);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(fellesformat.getMessage()).isEqualTo(message);
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erForesporsel()).isFalse());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erNotat()).isFalse());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.harVedlegg()).isTrue());
        assertions.assertAll();
    }
}