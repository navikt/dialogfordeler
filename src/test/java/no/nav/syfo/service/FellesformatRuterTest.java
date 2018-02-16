package no.nav.syfo.service;

import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.EiaQueueMottakInboundProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FellesformatRuterTest {
    @Mock
    private HodemeldingService hodemeldingService;
    @Mock
    private AppRecService appRecService;
    @Mock
    private EiaQueueMottakInboundProvider eiaQueueMottakInboundProvider;
    @InjectMocks
    private FellesformatRuter fellesformatRuter;

    @Test
    public void evaluerErSyfoHodemelding() {
        Hodemelding hodemelding = mock(Hodemelding.class);
        when(hodemelding.getDokIdNotatStream()).thenReturn(of("dokidnotat"));

        Fellesformat fellesformat = mock(Fellesformat.class);
        when(fellesformat.getHodemeldingStream()).thenAnswer(i -> of(hodemelding));
        when(fellesformat.getAppRecStream()).thenAnswer(i -> empty());

        fellesformatRuter.evaluer(fellesformat);

        verify(hodemeldingService).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).doSomething(any(AppRec.class));
        verify(eiaQueueMottakInboundProvider, never()).sendTilEia(any(Fellesformat.class));
    }

    @Test
    public void evaluerErSyfoAppRec() {
        AppRec appRec = mock(AppRec.class);
        when(appRec.originalMessageId()).thenReturn("enellera-nnen-uuid-elle-rnoesåntnoe!");

        Fellesformat fellesformat = mock(Fellesformat.class);
        when(fellesformat.getAppRecStream()).thenAnswer(i -> of(appRec));
        when(fellesformat.getHodemeldingStream()).thenAnswer(i -> empty());

        fellesformatRuter.evaluer(fellesformat);

        verify(appRecService).doSomething(any(AppRec.class));
        verify(hodemeldingService, never()).doSomething(any(Hodemelding.class));
        verify(eiaQueueMottakInboundProvider, never()).sendTilEia(any(Fellesformat.class));
    }

    @Test
    public void evaluerErEiamelding() {
        Hodemelding hodemelding = mock(Hodemelding.class);
        when(hodemelding.getDokIdNotatStream()).thenReturn(of("eiaDokidnotat"));

        Fellesformat fellesformat = mock(Fellesformat.class);
        when(fellesformat.getAppRecStream()).thenAnswer(i -> empty());
        when(fellesformat.getHodemeldingStream()).thenAnswer(i -> empty());

        fellesformatRuter.evaluer(fellesformat);

        verify(eiaQueueMottakInboundProvider).sendTilEia(any(Fellesformat.class));
        verify(hodemeldingService, never()).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).doSomething(any(AppRec.class));
    }
}