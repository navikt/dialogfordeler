package no.nav.syfo.service;

import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.MottakQueueEbrevKvitteringProvider;
import no.nav.syfo.provider.mq.MottakQueueEia2MeldingerProvider;
import no.nav.syfo.repository.MeldingIdRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.stream.Stream.of;
import static no.nav.syfo.domain.enums.FellesformatType.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FellesformatRuterTest {
    @Mock
    private SyfoMeldingService syfoMeldingService;
    @Mock
    private AppRecService appRecService;
    @Mock
    private MottakQueueEia2MeldingerProvider mottakQueueEia2MeldingerProvider;
    @Mock
    private MottakQueueEbrevKvitteringProvider mottakQueueEbrevKvitteringProvider;
    @Mock
    private MeldingIdRepository meldingIdRepository;
    @InjectMocks
    private FellesformatRuter fellesformatRuter;

    @Test
    public void evaluerErSyfoHodemelding() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        Hodemelding hodemelding = mock(Hodemelding.class);
        when(fellesformat.erAppRec()).thenReturn(false);
        when(fellesformat.getHodemeldingStream()).thenAnswer(i -> of(hodemelding));
        when(meldingIdRepository.finnMeldingstypeForDokumentIdSet(anySet())).thenReturn(Optional.of(SYFO_MELDING));

        fellesformatRuter.evaluer(fellesformat);

        verify(syfoMeldingService).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).registrerMottattAppRec(any(AppRec.class));
        verify(mottakQueueEia2MeldingerProvider, never()).sendTilEia(any(Fellesformat.class));
        verify(mottakQueueEbrevKvitteringProvider, never()).sendTilEMottak(anyString());
    }

    @Test
    public void evaluerErSyfoAppRec() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        AppRec appRec = mock(AppRec.class);
        when(fellesformat.erAppRec()).thenReturn(true);
        when(fellesformat.getAppRecStream()).thenAnswer(i -> of(appRec));
        when(meldingIdRepository.finnMeldingstypeForMeldingIdSet(anySet())).thenReturn(Optional.of(SYFO_MELDING));

        fellesformatRuter.evaluer(fellesformat);

        verify(appRecService).registrerMottattAppRec(any(AppRec.class));
        verify(syfoMeldingService, never()).doSomething(any(Hodemelding.class));
        verify(mottakQueueEia2MeldingerProvider, never()).sendTilEia(any(Fellesformat.class));
        verify(mottakQueueEbrevKvitteringProvider, never()).sendTilEMottak(anyString());
    }

    @Test
    public void evaluerErEiamelding() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        Hodemelding hodemelding = mock(Hodemelding.class);
        when(fellesformat.erAppRec()).thenReturn(false);
        when(fellesformat.getHodemeldingStream()).thenAnswer(i -> of(hodemelding));
        when(meldingIdRepository.finnMeldingstypeForDokumentIdSet(anySet())).thenReturn(Optional.empty());

        fellesformatRuter.evaluer(fellesformat);

        verify(mottakQueueEia2MeldingerProvider).sendTilEia(any(Fellesformat.class));
        verify(syfoMeldingService, never()).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).registrerMottattAppRec(any(AppRec.class));
        verify(mottakQueueEbrevKvitteringProvider, never()).sendTilEMottak(anyString());
    }

    @Test
    public void evaluerUkjentAppRec() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        AppRec appRec = mock(AppRec.class);
        when(fellesformat.erAppRec()).thenReturn(true);
        when(fellesformat.getAppRecStream()).thenAnswer(i -> of(appRec));
        when(meldingIdRepository.finnMeldingstypeForMeldingIdSet(anySet())).thenReturn(Optional.empty());
        when(fellesformat.getMessage()).thenReturn("AppRecMessage");

        fellesformatRuter.evaluer(fellesformat);

        verify(mottakQueueEbrevKvitteringProvider).sendTilEMottak(eq("AppRecMessage"));
        verify(mottakQueueEia2MeldingerProvider, never()).sendTilEia(any(Fellesformat.class));
        verify(syfoMeldingService, never()).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).registrerMottattAppRec(any(AppRec.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluerErSyfoHodemeldingFeilVedIdentifisering() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        Hodemelding hodemelding = mock(Hodemelding.class);
        when(fellesformat.erAppRec()).thenReturn(false);
        when(fellesformat.getHodemeldingStream()).thenAnswer(i -> of(hodemelding));
        when(meldingIdRepository.finnMeldingstypeForDokumentIdSet(anySet())).thenReturn(Optional.of(UKJENT_APPREC));

        fellesformatRuter.evaluer(fellesformat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluerErSyfoAppRecFeilVedIdentifisering() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        AppRec appRec = mock(AppRec.class);
        when(fellesformat.erAppRec()).thenReturn(true);
        when(fellesformat.getAppRecStream()).thenAnswer(i -> of(appRec));
        when(meldingIdRepository.finnMeldingstypeForMeldingIdSet(anySet())).thenReturn(Optional.of(EIA_MELDING));

        fellesformatRuter.evaluer(fellesformat);
    }
}