package no.nav.syfo.web.rest.dialogmelding;

import no.nav.syfo.provider.mq.MottakQueueUtsendingProvider;
import no.nav.syfo.web.rest.dialogmelding.model.RSDialogmelding;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static no.nav.syfo.domain.enums.FellesformatType.SYFO_HODEMELDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
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
        dialogmeldingService.registrerDialogmelding(new RSDialogmelding("meldingId", "lege", "pasient"));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(dialogmeldingRespository).registrerDialogmelding(eq("meldingId"), eq(SYFO_HODEMELDING));
        verify(mottakQueue).sendTilEMottak(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(ff());
    }

    private String ff() {
        return "<EI_fellesformat xmlns=\"http://www.nav.no/xml/eiff/2/\" xmlns:ns6=\"http://www.kith.no/xmlstds/base64container\" xmlns:ns5=\"http://www.kith.no/xmlstds/felleskomponent1\" xmlns:ns2=\"http://www.kith.no/xmlstds/msghead/2006-05-24\" xmlns:ns4=\"http://www.kith.no/xmlstds/dialog/2006-10-11\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "    <ns2:MsgHead>\n" +
                "        <ns2:Document>\n" +
                "            <ns2:RefDoc>\n" +
                "                <ns2:Content>\n" +
                "                    <ns4:Dialogmelding>\n" +
                "                        <ns4:Foresporsel>\n" +
                "                            <ns4:DokIdForesp>meldingId</ns4:DokIdForesp>\n" +
                "                        </ns4:Foresporsel>\n" +
                "                    </ns4:Dialogmelding>\n" +
                "                </ns2:Content>\n" +
                "            </ns2:RefDoc>\n" +
                "        </ns2:Document>\n" +
                "    </ns2:MsgHead>\n" +
                "</EI_fellesformat>";
    }
}