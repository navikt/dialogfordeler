package no.nav.syfo.service;

import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.EiaQueueMottakInboundProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.stream.Stream.of;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HodemeldingRuterTest {
    @Mock
    private HodemeldingService hodemeldingService;
    @Mock
    private EiaQueueMottakInboundProvider eiaQueueMottakInboundProvider;
    @InjectMocks
    private HodemeldingRuter hodemeldingRuter;

    @Test
    public void evaluerErSyfomelding() {
        Hodemelding hodemelding = mock(Hodemelding.class);
        when(hodemelding.getDokIdNotatStream()).thenReturn(of("dokidnotat"));

        hodemeldingRuter.evaluer(hodemelding);

        verify(hodemeldingService).doSomething(any(Hodemelding.class));
        verify(eiaQueueMottakInboundProvider, never()).sendTilEia(any(Hodemelding.class));
    }

    @Test
    public void evaluerErEiamelding() {
        Hodemelding hodemelding = mock(Hodemelding.class);
        when(hodemelding.getDokIdNotatStream()).thenReturn(of("eiaDokidnotat"));

        hodemeldingRuter.evaluer(hodemelding);

        verify(eiaQueueMottakInboundProvider).sendTilEia(any(Hodemelding.class));
        verify(hodemeldingService, never()).doSomething(any(Hodemelding.class));
    }
}