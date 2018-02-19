package no.nav.syfo.domain;

import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.util.JAXB;
import no.trygdeetaten.xml.eiff._1.XMLEIFellesformat;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import javax.jms.TextMessage;

import static no.nav.syfo.testdata.AppRecXml.APPREC1_1;
import static no.nav.syfo.testdata.AppRecXml.apprec;
import static no.nav.syfo.testdata.FellesformatXml.fellesformat;
import static no.nav.syfo.testdata.HodemeldingXml.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FellesformatTest {

    @Test
    public void erForesporsel() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        String melding = fellesformat(hodemelding(DIALOG0));
        when(textMessage.getText()).thenReturn(melding);
        XMLEIFellesformat xmlFellesformat = JAXB.unmarshalMelding(melding);
        Fellesformat fellesformat = new Fellesformat(textMessage, xmlFellesformat);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erForesporsel()).isTrue());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erNotat()).isFalse());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.harVedlegg()).isFalse());
        assertions.assertAll();
    }

    @Test
    public void erNotat() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        String melding = fellesformat(hodemelding(NOTAT1));
        when(textMessage.getText()).thenReturn(melding);
        XMLEIFellesformat xmlFellesformat = JAXB.unmarshalMelding(melding);
        Fellesformat fellesformat = new Fellesformat(textMessage, xmlFellesformat);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erForesporsel()).isFalse());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erNotat()).isTrue());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.harVedlegg()).isFalse());
        assertions.assertAll();
    }

    @Test
    public void harVedlegg() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        String melding = fellesformat(hodemelding(VEDLEGG));
        when(textMessage.getText()).thenReturn(melding);
        XMLEIFellesformat xmlFellesformat = JAXB.unmarshalMelding(melding);
        Fellesformat fellesformat = new Fellesformat(textMessage, xmlFellesformat);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erForesporsel()).isFalse());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.erNotat()).isFalse());
        assertions.assertThat(fellesformat.getHodemeldingStream()).allSatisfy(hodemelding -> assertThat(hodemelding.harVedlegg()).isTrue());
        assertions.assertAll();
    }

    @Test
    public void erSyfoHodemelding() throws Exception {
        //TODO: Fellesformat.erSyfoHodemelding mangler implementasjon
    }

    @Test
    public void erIkkeSyfoHodemelding() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        String melding = fellesformat(hodemelding(NOTAT0 | NOTAT1));
        when(textMessage.getText()).thenReturn(melding);
        XMLEIFellesformat xmlFellesformat = JAXB.unmarshalMelding(melding);
        Fellesformat fellesformat = new Fellesformat(textMessage, xmlFellesformat);

        assertThat(fellesformat.erSyfoHodemelding()).isFalse();
    }

    @Test
    public void erSyfoAppRec() throws Exception {
        //TODO: Fellesformat.erSyfoAppRec mangler implementasjon
    }

    @Test
    public void erIkkeSyfoAppRec() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        String melding = fellesformat(apprec(APPREC1_1));
        when(textMessage.getText()).thenReturn(melding);
        XMLEIFellesformat xmlFellesformat = JAXB.unmarshalMelding(melding);
        Fellesformat fellesformat = new Fellesformat(textMessage, xmlFellesformat);

        assertThat(fellesformat.erSyfoAppRec()).isFalse();
    }
}