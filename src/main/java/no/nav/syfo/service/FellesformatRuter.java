package no.nav.syfo.service;

import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.enums.FellesformatType;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.MottakQueueEbrevKvittering;
import no.nav.syfo.provider.mq.MottakQueueEia2MeldingerProvider;
import no.nav.syfo.repository.MeldingIdRepository;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toSet;
import static no.nav.syfo.domain.enums.FellesformatType.EIA_MELDING;
import static no.nav.syfo.domain.enums.FellesformatType.UKJENT_APPREC;

@Service
public class FellesformatRuter {
    private AppRecService appRecService;
    private SyfoMeldingService syfoMeldingService;
    private MottakQueueEia2MeldingerProvider mottakQueueEia2MeldingerProvider;
    private MottakQueueEbrevKvittering mottakQueueEbrevKvittering;

    private MeldingIdRepository meldingIdRepository;

    public FellesformatRuter(AppRecService appRecService,
                             SyfoMeldingService syfoMeldingService,
                             MottakQueueEia2MeldingerProvider mottakQueueEia2MeldingerProvider,
                             MottakQueueEbrevKvittering mottakQueueEbrevKvittering,
                             MeldingIdRepository meldingIdRepository) {
        this.appRecService = appRecService;
        this.syfoMeldingService = syfoMeldingService;
        this.mottakQueueEia2MeldingerProvider = mottakQueueEia2MeldingerProvider;
        this.mottakQueueEbrevKvittering = mottakQueueEbrevKvittering;
        this.meldingIdRepository = meldingIdRepository;
    }

    public void evaluer(Fellesformat fellesformat) {
        if (fellesformat.erAppRec()) {
            evaluerAppRec(fellesformat);
        } else {
            evaluerHodemelding(fellesformat);
        }
    }

    private void evaluerAppRec(Fellesformat fellesformat) {
        switch (identifiserAppRec(fellesformat)) {
            case SYFO_MELDING:
                fellesformat.getAppRecStream().forEach(appRecService::doSomething);
                break;
            case UKJENT_APPREC:
                mottakQueueEbrevKvittering.sendTilEMottak(fellesformat.getMessage());
                break;
            default:
                throw new IllegalArgumentException("Kan ikke identifisere AppRec");
        }
    }

    private void evaluerHodemelding(Fellesformat fellesformat) {
        switch (identifiserHodemelding(fellesformat)) {
            case SYFO_MELDING:
                fellesformat.getHodemeldingStream().forEach(syfoMeldingService::doSomething);
                break;
            case EIA_MELDING:
                mottakQueueEia2MeldingerProvider.sendTilEia(fellesformat);
                break;
            default:
                throw new IllegalArgumentException("Kan ikke identifisere melding");
        }
    }

    private FellesformatType identifiserAppRec(Fellesformat fellesformat) {
        return meldingIdRepository.finnMeldingstypeForMeldingIdSet(
                fellesformat
                        .getAppRecStream()
                        .map(AppRec::originalMessageId)
                        .collect(toSet()))
                .orElse(UKJENT_APPREC);
    }

    private FellesformatType identifiserHodemelding(Fellesformat fellesformat) {
        return meldingIdRepository.finnMeldingstypeForDokumentIdSet(
                fellesformat
                        .getHodemeldingStream()
                        .flatMap(Hodemelding::getDokIdNotatStream)
                        .collect(toSet()))
                .orElse(EIA_MELDING);
    }
}
