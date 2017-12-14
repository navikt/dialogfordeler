package no.nav.syfo.domain;

import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import javax.jms.TextMessage;

import static java.util.stream.Collectors.toList;
import static no.nav.syfo.testdata.HodemeldingXml.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HodemeldingTest {

    @Test
    public void erForesporsel() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getText()).thenReturn(hodemelding(DIALOG0));

        Hodemelding hodemelding = new Hodemelding(textMessage);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(hodemelding.erForesporsel()).isTrue();
        assertions.assertThat(hodemelding.erNotat()).isFalse();
        assertions.assertThat(hodemelding.harVedlegg()).isFalse();
        assertions.assertAll();
    }

    @Test
    public void erNotat() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getText()).thenReturn(hodemelding(NOTAT1));

        Hodemelding hodemelding = new Hodemelding(textMessage);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(hodemelding.erNotat()).isTrue();
        assertions.assertThat(hodemelding.erForesporsel()).isFalse();
        assertions.assertThat(hodemelding.harVedlegg()).isFalse();
        assertions.assertAll();
    }

    @Test
    public void harVedlegg() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getText()).thenReturn(hodemelding(VEDLEGG));

        Hodemelding hodemelding = new Hodemelding(textMessage);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(hodemelding.harVedlegg()).isTrue();
        assertions.assertThat(hodemelding.erForesporsel()).isFalse();
        assertions.assertThat(hodemelding.erNotat()).isFalse();
        assertions.assertAll();
    }

    @Test
    public void getDokIdNotatStream() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getText()).thenReturn(hodemelding(NOTAT0 | NOTAT1));

        Hodemelding hodemelding = new Hodemelding(textMessage);

        assertThat(hodemelding.getDokIdNotatStream().collect(toList())).hasSize(2)
                .containsExactly("dokidnotat1_0", "dokidnotat1_1");
    }
}