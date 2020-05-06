package no.nav.syfo.service;

import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.enums.MeldingLoggType;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.MottakQueueEbrevKvitteringProvider;
import no.nav.syfo.provider.mq.MottakQueueEia2MeldingerProvider;
import no.nav.syfo.provider.mq.MottakQueuePadm2MeldingerProvider;
import no.nav.syfo.repository.MeldingLoggRepository;
import no.nav.syfo.repository.MeldingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.stream.Stream.of;
import static no.nav.syfo.domain.enums.FellesformatType.*;
import static no.nav.syfo.domain.enums.MeldingLoggType.INNKOMMENDE_APPREC;
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
    private MottakQueuePadm2MeldingerProvider mottakQueuePadm2MeldingerProvider;
    @Mock
    private MottakQueueEbrevKvitteringProvider mottakQueueEbrevKvitteringProvider;
    @Mock
    private MeldingRepository meldingRepository;
    @Mock
    private MeldingLoggRepository meldingLoggRepository;
    @InjectMocks
    private FellesformatRuter fellesformatRuter;

    @Test
    public void evaluerErSyfoHodemelding() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        Hodemelding hodemelding = mock(Hodemelding.class);
        when(fellesformat.erAppRec()).thenReturn(false);
        when(fellesformat.getHodemeldingStream()).thenAnswer(i -> of(hodemelding));
        when(meldingRepository.finnMeldingstypeForDokumentIdSet(anySet())).thenReturn(Optional.of(SYFO_MELDING));

        fellesformatRuter.evaluer(fellesformat);

        verify(syfoMeldingService).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).registrerMottattAppRec(any(AppRec.class));
        verify(meldingLoggRepository, never()).loggMelding(anyString(), anyLong(), eq(INNKOMMENDE_APPREC));
        verify(mottakQueueEia2MeldingerProvider, never()).sendTilEia(any(Fellesformat.class));
        verify(mottakQueueEbrevKvitteringProvider, never()).sendTilEMottak(anyString());
    }

    @Test
    public void evaluerErSyfoAppRec() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        AppRec appRec = mock(AppRec.class);
        when(fellesformat.erAppRec()).thenReturn(true);
        when(fellesformat.getAppRecStream()).thenAnswer(i -> of(appRec));
        when(appRecService.registrerMottattAppRec(any(AppRec.class))).thenReturn(true);
        when(meldingRepository.finnMeldingstypeForMeldingIdSet(anySet())).thenReturn(Optional.of(SYFO_MELDING));
        when(fellesformat.getMessage()).thenReturn("AppRecMessage");

        fellesformatRuter.evaluer(fellesformat);

        verify(appRecService).registrerMottattAppRec(any(AppRec.class));
        verify(meldingLoggRepository).loggMelding(eq("AppRecMessage"), anyLong(), eq(INNKOMMENDE_APPREC));
        verify(syfoMeldingService, never()).doSomething(any(Hodemelding.class));
        verify(mottakQueueEia2MeldingerProvider, never()).sendTilEia(any(Fellesformat.class));
        verify(mottakQueueEbrevKvitteringProvider, never()).sendTilEMottak(anyString());
    }

    @Test
    public void evaluerErSyfoAppRecIkkeLoggMeldingHvisDuplikat() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        AppRec appRec = mock(AppRec.class);
        when(fellesformat.erAppRec()).thenReturn(true);
        when(fellesformat.getAppRecStream()).thenAnswer(i -> of(appRec));
        when(appRecService.registrerMottattAppRec(any(AppRec.class))).thenReturn(false);
        when(meldingRepository.finnMeldingstypeForMeldingIdSet(anySet())).thenReturn(Optional.of(SYFO_MELDING));

        fellesformatRuter.evaluer(fellesformat);

        verify(appRecService).registrerMottattAppRec(any(AppRec.class));
        verify(meldingLoggRepository, never()).loggMelding(anyString(), anyLong(), any(MeldingLoggType.class));
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
        when(meldingRepository.finnMeldingstypeForDokumentIdSet(anySet())).thenReturn(Optional.empty());

        fellesformatRuter.evaluer(fellesformat);

        verify(mottakQueueEia2MeldingerProvider).sendTilEia(any(Fellesformat.class));
        verify(syfoMeldingService, never()).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).registrerMottattAppRec(any(AppRec.class));
        verify(meldingLoggRepository, never()).loggMelding(anyString(), anyLong(), eq(INNKOMMENDE_APPREC));
        verify(mottakQueueEbrevKvitteringProvider, never()).sendTilEMottak(anyString());
    }

    @Test
    public void evaluerUkjentAppRec() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        AppRec appRec = mock(AppRec.class);
        when(fellesformat.erAppRec()).thenReturn(true);
        when(fellesformat.getAppRecStream()).thenAnswer(i -> of(appRec));
        when(meldingRepository.finnMeldingstypeForMeldingIdSet(anySet())).thenReturn(Optional.empty());
        when(fellesformat.getMessage()).thenReturn("AppRecMessage");

        fellesformatRuter.evaluer(fellesformat);

        verify(mottakQueueEbrevKvitteringProvider).sendTilEMottak(eq("AppRecMessage"));
        verify(mottakQueueEia2MeldingerProvider, never()).sendTilEia(any(Fellesformat.class));
        verify(syfoMeldingService, never()).doSomething(any(Hodemelding.class));
        verify(appRecService, never()).registrerMottattAppRec(any(AppRec.class));
        verify(meldingLoggRepository, never()).loggMelding(anyString(), anyLong(), eq(INNKOMMENDE_APPREC));
    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluerErSyfoHodemeldingFeilVedIdentifisering() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        Hodemelding hodemelding = mock(Hodemelding.class);
        when(fellesformat.erAppRec()).thenReturn(false);
        when(fellesformat.getHodemeldingStream()).thenAnswer(i -> of(hodemelding));
        when(meldingRepository.finnMeldingstypeForDokumentIdSet(anySet())).thenReturn(Optional.of(UKJENT_APPREC));

        fellesformatRuter.evaluer(fellesformat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluerErSyfoAppRecFeilVedIdentifisering() {
        Fellesformat fellesformat = mock(Fellesformat.class);
        AppRec appRec = mock(AppRec.class);
        when(fellesformat.erAppRec()).thenReturn(true);
        when(fellesformat.getAppRecStream()).thenAnswer(i -> of(appRec));
        when(meldingRepository.finnMeldingstypeForMeldingIdSet(anySet())).thenReturn(Optional.of(EIA_MELDING));

        fellesformatRuter.evaluer(fellesformat);
    }
}
