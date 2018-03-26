package no.nav.syfo.web.rest.dialogmelding;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.MottakQueueUtsendingProvider;
import no.nav.syfo.util.JAXB;
import no.nav.syfo.web.rest.dialogmelding.converter.FellesformatConverter;
import no.nav.syfo.web.rest.dialogmelding.model.RSHodemelding;
import no.nav.xml.eiff._2.XMLEIFellesformat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static no.nav.syfo.domain.enums.FellesformatType.SYFO_MELDING;

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
    public void registrerDialogmelding(RSHodemelding dialogmelding) {
        Fellesformat fellesformat = opprettDialogmelding(dialogmelding);

        dialogmeldingRespository.registrerDialogmelding(fellesformat
                        .getHodemeldingStream()
                        .flatMap(Hodemelding::getDokIdForespStream)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Finner ikke dokumentId")),
                fellesformat
                        .getHodemeldingStream()
                        .map(Hodemelding::getMessageId)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Finner ikke meldingId")),
                SYFO_MELDING);

        mottakQueue.sendTilEMottak(fellesformat.getMessage());
    }

    private Fellesformat opprettDialogmelding(RSHodemelding hodemelding) {
        XMLEIFellesformat xmleiFellesformat = new FellesformatConverter(hodemelding).getEiFellesformat();
        return new Fellesformat(xmleiFellesformat, JAXB::marshallDialogmelding1_0);
    }
}
