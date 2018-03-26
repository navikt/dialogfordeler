package no.nav.syfo.web.rest.dialogmelding;

import no.nav.syfo.provider.mq.MottakQueueUtsendingProvider;
import no.nav.syfo.web.rest.dialogmelding.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static no.nav.syfo.domain.enums.FellesformatType.SYFO_MELDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DialogmeldingServiceTest {
    @Mock
    private DialogmeldingRespository dialogmeldingRespository;
    @Mock
    private MottakQueueUtsendingProvider mottakQueue;
    @InjectMocks
    private DialogmeldingService dialogmeldingService;

    @Test
    public void registrerDialogmelding() {
        dialogmeldingService.registrerDialogmelding(hodemelding());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(dialogmeldingRespository).registrerDialogmelding(anyString(), anyString(), eq(SYFO_MELDING));
        verify(mottakQueue).sendTilEMottak(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).hasSize(5927);
    }

    private RSHodemelding hodemelding() {
        return new RSHodemelding(
                new RSMeldingInfo(
                        new RSSender("id", "navn"),
                        new RSMottaker("herId",
                                "partnerId",
                                "orgnummer",
                                "navn",
                                "adresse",
                                "postnummer",
                                "poststed",
                                new RSBehandler(
                                        "fnr",
                                        "hprId",
                                        "fornavn",
                                        "mellomnavn",
                                        "etternavn")),
                        new RSPasient(
                                "fnr",
                                "fornavn",
                                "mellomnavn",
                                "etternavn")),
                new RSDialogmelding(
                        "sporsmal",
                        new RSRoller(
                                "personFornavn",
                                "personMellomnavn",
                                "personEtternavn")),
                new RSVedlegg(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    }
}