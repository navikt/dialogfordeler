package no.nav.syfo.service;

import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.MottakQueueEia2MeldingerProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    private MottakQueueEia2MeldingerProvider mottakQueueEia2MeldingerProvider;
    @InjectMocks
    private FellesformatRuter fellesformatRuter;

    @Test
    public void evaluerErSyfoHodemelding() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        when(fellesformat.getHodemeldingStream()).thenAnswer(i -> of(mock(Hodemelding.class)));
        when(fellesformat.erSyfoHodemelding()).thenReturn(true);

        fellesformatRuter.evaluer(fellesformat);

        verify(hodemeldingService).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).doSomething(any(AppRec.class));
        verify(mottakQueueEia2MeldingerProvider, never()).sendTilEia(any(Fellesformat.class));
    }

    @Test
    public void evaluerErSyfoAppRec() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        when(fellesformat.getAppRecStream()).thenAnswer(i -> of(mock(AppRec.class)));
        when(fellesformat.erSyfoAppRec()).thenReturn(true);

        fellesformatRuter.evaluer(fellesformat);

        verify(appRecService).doSomething(any(AppRec.class));
        verify(hodemeldingService, never()).doSomething(any(Hodemelding.class));
        verify(mottakQueueEia2MeldingerProvider, never()).sendTilEia(any(Fellesformat.class));
    }

    @Test
    public void evaluerErEiamelding() {
        Fellesformat fellesformat = mock(Fellesformat.class);

        fellesformatRuter.evaluer(fellesformat);

        verify(mottakQueueEia2MeldingerProvider).sendTilEia(any(Fellesformat.class));
        verify(hodemeldingService, never()).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).doSomething(any(AppRec.class));
    }
}