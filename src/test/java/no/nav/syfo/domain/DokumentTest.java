package no.nav.syfo.domain;

import no.kith.xmlstds.base64container.XMLBase64Container;
import no.kith.xmlstds.msghead._2006_05_24.XMLDocument;
import no.kith.xmlstds.msghead._2006_05_24.XMLRefDoc;
import no.nav.syfo.domain.hodemeldingwrapper.Dialogmelding1_0;
import no.nav.syfo.domain.hodemeldingwrapper.Dialogmelding1_1;
import no.nav.syfo.domain.hodemeldingwrapper.Dokument;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DokumentTest {

    @Test
    public void oppretterDialogmelding1_0() {
        Dokument dokument = new Dokument(new XMLDocument().withRefDoc(new XMLRefDoc().withContent(
                new XMLRefDoc.Content().withAny(new no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding()))));
        assertThat(dokument.getDialogmeldingListe()).hasSize(1).allSatisfy(dialogmelding ->
                assertThat(dialogmelding).isInstanceOf(Dialogmelding1_0.class));
    }

    @Test
    public void oppretterDialogmelding1_1() {
        Dokument dokument = new Dokument(new XMLDocument().withRefDoc(new XMLRefDoc().withContent(
                new XMLRefDoc.Content().withAny(new no.kith.xmlstds.dialog._2013_01_23.XMLDialogmelding()))));
        assertThat(dokument.getDialogmeldingListe()).hasSize(1).allSatisfy(dialogmelding ->
                assertThat(dialogmelding).isInstanceOf(Dialogmelding1_1.class));
    }

    @Test
    public void erForesporsel() {
        Dokument dokument = new Dokument(new XMLDocument().withRefDoc(new XMLRefDoc().withContent(
                new XMLRefDoc.Content().withAny(new no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding().withForesporsel(
                        new no.kith.xmlstds.dialog._2006_10_11.XMLForesporsel())))));
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(dokument.erForesporsel()).isTrue();
        assertions.assertThat(dokument.erNotat()).isFalse();
        assertions.assertThat(dokument.harVedlegg()).isFalse();
        assertions.assertAll();
    }

    @Test
    public void erNotat() {
        Dokument dokument = new Dokument(new XMLDocument().withRefDoc(new XMLRefDoc().withContent(
                new XMLRefDoc.Content().withAny(new no.kith.xmlstds.dialog._2013_01_23.XMLDialogmelding().withNotat(
                        new no.kith.xmlstds.dialog._2013_01_23.XMLNotat())))));
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(dokument.erNotat()).isTrue();
        assertions.assertThat(dokument.erForesporsel()).isFalse();
        assertions.assertThat(dokument.harVedlegg()).isFalse();
        assertions.assertAll();
    }

    @Test
    public void harVedlegg() {
        Dokument dokument = new Dokument(new XMLDocument().withRefDoc(new XMLRefDoc().withContent(
                new XMLRefDoc.Content().withAny(new XMLBase64Container()))));
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(dokument.harVedlegg()).isTrue();
        assertions.assertThat(dokument.erForesporsel()).isFalse();
        assertions.assertThat(dokument.erNotat()).isFalse();
        assertions.assertAll();
    }

    @Test
    public void getDokIdNotatStream() {
        Dokument dokument = new Dokument(new XMLDocument().withRefDoc(new XMLRefDoc().withContent(
                new XMLRefDoc.Content().withAny(
                        new no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding().withNotat(
                                new no.kith.xmlstds.dialog._2006_10_11.XMLNotat().withDokIdNotat("DokIdNotat1_0")),
                        new no.kith.xmlstds.dialog._2013_01_23.XMLDialogmelding().withNotat(
                                new no.kith.xmlstds.dialog._2013_01_23.XMLNotat().withDokIdNotat("DokIdNotat1_1"))))));
        assertThat(dokument.getDokIdNotatStream()).hasSize(2).containsExactly("DokIdNotat1_0", "DokIdNotat1_1");
    }
}