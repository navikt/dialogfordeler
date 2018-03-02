package no.nav.syfo.web.rest.dialogmelding;

import lombok.extern.slf4j.Slf4j;
import no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding;
import no.kith.xmlstds.dialog._2006_10_11.XMLForesporsel;
import no.kith.xmlstds.msghead._2006_05_24.XMLDocument;
import no.kith.xmlstds.msghead._2006_05_24.XMLMsgHead;
import no.kith.xmlstds.msghead._2006_05_24.XMLRefDoc;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.provider.mq.MottakQueueUtsendingProvider;
import no.nav.syfo.util.JAXB;
import no.nav.syfo.web.rest.dialogmelding.model.RSDialogmelding;
import no.nav.xml.eiff._2.XMLEIFellesformat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static no.nav.syfo.domain.enums.FellesformatType.SYFO_HODEMELDING;

@Service
@Slf4j
public class DialogmeldingService {
    private DialogmeldingRespository dialogmeldingRespository;
    private MottakQueueUtsendingProvider mottakQueue;

    public DialogmeldingService(DialogmeldingRespository dialogmeldingRespository, MottakQueueUtsendingProvider mottakQueue) {
        this.dialogmeldingRespository = dialogmeldingRespository;
        this.mottakQueue = mottakQueue;
    }

    @Transactional
    public void registrerDialogmelding(RSDialogmelding dialogmelding) {
        dialogmeldingRespository.registrerDialogmelding(dialogmelding.getMeldingId(), SYFO_HODEMELDING);

        Fellesformat fellesformat = opprettDialogmelding(dialogmelding);

        mottakQueue.sendTilEMottak(fellesformat.getMessage());
    }

    private Fellesformat opprettDialogmelding(RSDialogmelding dialogmelding) {
        Fellesformat fellesformat = new Fellesformat(
                new XMLEIFellesformat().withAny(
                        new XMLMsgHead().withDocument(
                                new XMLDocument().withRefDoc(
                                        new XMLRefDoc().withContent(
                                                new XMLRefDoc.Content().withAny(
                                                        new XMLDialogmelding().withForesporsel(
                                                                new XMLForesporsel().withDokIdForesp(dialogmelding.getMeldingId()))))))));
        fellesformat.setMessage(JAXB.marshallDialogmelding1_0(fellesformat.getEIFellesformat()));
        return fellesformat;
    }
}
