package no.nav.syfo.service;

import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.repository.AppRecRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppRecServiceTest {
    @Mock
    private AppRecRepository appRecRepository;
    @InjectMocks
    private AppRecService appRecService;

    @Test
    public void registrerMottattAppRec() throws Exception {
        AppRec appRec = mock(AppRec.class);
        when(appRec.originalMessageId()).thenReturn("meldingId");

        appRecService.registrerMottattAppRec(appRec);

        verify(appRec).originalMessageId();
        verify(appRecRepository).registrerMottattAppRec("meldingId");
    }
}