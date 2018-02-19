package no.nav.syfo.domain;

import no.kith.xmlstds.base64container.XMLBase64Container;
import no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding;
import no.kith.xmlstds.dialog._2006_10_11.XMLForesporsel;
import no.kith.xmlstds.dialog._2006_10_11.XMLNotat;
import no.kith.xmlstds.msghead._2006_05_24.XMLDocument;
import no.kith.xmlstds.msghead._2006_05_24.XMLMsgHead;
import no.kith.xmlstds.msghead._2006_05_24.XMLRefDoc;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class HodemeldingTest {

    @Test
    public void erForesporsel() throws Exception {
        XMLMsgHead xmlMsgHead = new XMLMsgHead()
                .withDocument(new XMLDocument().withRefDoc(new XMLRefDoc()
                        .withContent(new XMLRefDoc.Content().withAny(new XMLDialogmelding()
                                .withForesporsel(new XMLForesporsel())))));
        Hodemelding hodemelding = new Hodemelding(xmlMsgHead);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(hodemelding.erForesporsel()).isTrue();
        assertions.assertThat(hodemelding.erNotat()).isFalse();
        assertions.assertThat(hodemelding.harVedlegg()).isFalse();
        assertions.assertAll();
    }

    @Test
    public void erNotat() throws Exception {
        XMLMsgHead xmlMsgHead = new XMLMsgHead()
                .withDocument(new XMLDocument().withRefDoc(new XMLRefDoc()
                        .withContent(new XMLRefDoc.Content().withAny(new XMLDialogmelding()
                                .withNotat(new XMLNotat())))));
        Hodemelding hodemelding = new Hodemelding(xmlMsgHead);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(hodemelding.erNotat()).isTrue();
        assertions.assertThat(hodemelding.erForesporsel()).isFalse();
        assertions.assertThat(hodemelding.harVedlegg()).isFalse();
        assertions.assertAll();
    }

    @Test
    public void harVedlegg() throws Exception {
        XMLMsgHead xmlMsgHead = new XMLMsgHead()
                .withDocument(new XMLDocument().withRefDoc(new XMLRefDoc()
                        .withContent(new XMLRefDoc.Content().withAny(new XMLBase64Container()))));
        Hodemelding hodemelding = new Hodemelding(xmlMsgHead);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(hodemelding.harVedlegg()).isTrue();
        assertions.assertThat(hodemelding.erForesporsel()).isFalse();
        assertions.assertThat(hodemelding.erNotat()).isFalse();
        assertions.assertAll();
    }

    @Test
    public void getDokIdNotatStream() throws Exception {
        XMLMsgHead xmlMsgHead = new XMLMsgHead()
                .withDocument(new XMLDocument().withRefDoc(new XMLRefDoc()
                        .withContent(new XMLRefDoc.Content().withAny(
                                new XMLDialogmelding().withNotat(
                                        new XMLNotat().withDokIdNotat("DokIdNotat1")),
                                new XMLDialogmelding().withNotat(
                                        new XMLNotat().withDokIdNotat("DokIdNotat2"))))));
        Hodemelding hodemelding = new Hodemelding(xmlMsgHead);

        assertThat(hodemelding.getDokIdNotatStream().collect(toList())).hasSize(2)
                .containsExactly("DokIdNotat1", "DokIdNotat2");
    }
}