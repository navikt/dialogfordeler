package no.nav.syfo.service;

import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.exception.MeldingInboundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.jms.TextMessage;

import static no.nav.syfo.testdata.FellesformatXml.fellesformat;
import static no.nav.syfo.testdata.HodemeldingXml.NOTAT1;
import static no.nav.syfo.testdata.HodemeldingXml.hodemelding;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MeldingRuterTest {
    @Mock
    private FellesformatRuter fellesformatRuter;
    @InjectMocks
    private MeldingRuter meldingRuter;

    @Test
    public void evaluerFellesformat() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getText()).thenReturn(fellesformat(hodemelding(NOTAT1)));

        meldingRuter.evaluer(textMessage);

        verify(fellesformatRuter).evaluer(any(Fellesformat.class));
    }

    @Test(expected = MeldingInboundException.class)
    public void evaluerIkkeFellesformat() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getText()).thenReturn(hodemelding(0));

        meldingRuter.evaluer(textMessage);
    }
}