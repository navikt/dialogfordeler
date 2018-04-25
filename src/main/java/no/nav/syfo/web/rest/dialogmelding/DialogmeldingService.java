package no.nav.syfo.web.rest.dialogmelding;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.MottakQueueUtsendingProvider;
import no.nav.syfo.repository.MeldingLoggRepository;
import no.nav.syfo.util.JAXB;
import no.nav.syfo.web.rest.dialogmelding.converter.FellesformatConverter;
import no.nav.syfo.web.rest.dialogmelding.model.RSHodemelding;
import no.nav.xml.eiff._2.XMLEIFellesformat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static no.nav.syfo.domain.enums.FellesformatType.SYFO_MELDING;
import static no.nav.syfo.domain.enums.MeldingLoggType.UTAAENDE_MELDING;

@Service
@Slf4j
public class DialogmeldingService {
    private DialogmeldingRespository dialogmeldingRespository;
    private MeldingLoggRepository meldingLoggRepository;
    private MottakQueueUtsendingProvider mottakQueue;

    public DialogmeldingService(DialogmeldingRespository dialogmeldingRespository,
                                MeldingLoggRepository meldingLoggRepository,
                                MottakQueueUtsendingProvider mottakQueue) {
        this.dialogmeldingRespository = dialogmeldingRespository;
        this.meldingLoggRepository = meldingLoggRepository;
        this.mottakQueue = mottakQueue;
    }

    @Transactional
    public void registrerDialogmelding(RSHodemelding dialogmelding) {
        Fellesformat fellesformat = opprettDialogmelding(dialogmelding);

        Long meldingId = dialogmeldingRespository.registrerDialogmelding(fellesformat
                        .getHodemeldingStream()
                        .flatMap(Hodemelding::getDokIdNotatStream)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Finner ikke dokumentId")),
                fellesformat
                        .getHodemeldingStream()
                        .map(Hodemelding::getMessageId)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Finner ikke meldingId")),
                SYFO_MELDING);

        mottakQueue.sendTilEMottak(fellesformat.getMessage());
        meldingLoggRepository.loggMelding(fellesformat.getMessage(), meldingId, UTAAENDE_MELDING);
    }

    private Fellesformat opprettDialogmelding(RSHodemelding hodemelding) {
        XMLEIFellesformat xmleiFellesformat = new FellesformatConverter(hodemelding).getEiFellesformat();
        return new Fellesformat(xmleiFellesformat, JAXB::marshallDialogmelding1_0);
    }
}
