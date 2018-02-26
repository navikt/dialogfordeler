package no.nav.syfo.service;

import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.MottakQueueEia2MeldingerProvider;
import no.nav.syfo.repository.MeldingIdRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.stream.Stream.of;
import static no.nav.syfo.domain.enums.FellesformatType.SYFO_APPREC;
import static no.nav.syfo.domain.enums.FellesformatType.SYFO_HODEMELDING;
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
    @Mock
    private MeldingIdRepository meldingIdRepository;
    @InjectMocks
    private FellesformatRuter fellesformatRuter;

    @Test
    public void evaluerErSyfoHodemelding() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        when(fellesformat.meldingIdStream()).thenAnswer(i -> of("Hodemelding"));
        when(meldingIdRepository.finnMeldingstype(anySet())).thenReturn(Optional.of(SYFO_HODEMELDING));
        when(fellesformat.getHodemeldingStream()).thenAnswer(i -> of(mock(Hodemelding.class)));

        fellesformatRuter.evaluer(fellesformat);

        verify(hodemeldingService).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).doSomething(any(AppRec.class));
        verify(mottakQueueEia2MeldingerProvider, never()).sendTilEia(any(Fellesformat.class));
    }

    @Test
    public void evaluerErSyfoAppRec() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        when(fellesformat.meldingIdStream()).thenAnswer(i -> of("AppRec"));
        when(meldingIdRepository.finnMeldingstype(anySet())).thenReturn(Optional.of(SYFO_APPREC));
        when(fellesformat.getAppRecStream()).thenAnswer(i -> of(mock(AppRec.class)));

        fellesformatRuter.evaluer(fellesformat);

        verify(appRecService).doSomething(any(AppRec.class));
        verify(hodemeldingService, never()).doSomething(any(Hodemelding.class));
        verify(mottakQueueEia2MeldingerProvider, never()).sendTilEia(any(Fellesformat.class));
    }

    @Test
    public void evaluerErEiamelding() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        when(fellesformat.meldingIdStream()).thenAnswer(i -> of("Eiamelding"));
        when(meldingIdRepository.finnMeldingstype(anySet())).thenReturn(Optional.empty());

        fellesformatRuter.evaluer(fellesformat);

        verify(mottakQueueEia2MeldingerProvider).sendTilEia(any(Fellesformat.class));
        verify(hodemeldingService, never()).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).doSomething(any(AppRec.class));
    }
}