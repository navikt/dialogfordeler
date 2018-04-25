package no.nav.syfo.service;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.enums.FellesformatType;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.MottakQueueEbrevKvitteringProvider;
import no.nav.syfo.provider.mq.MottakQueueEia2MeldingerProvider;
import no.nav.syfo.repository.MeldingLoggRepository;
import no.nav.syfo.repository.MeldingRepository;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;
import static no.nav.syfo.domain.enums.FellesformatType.EIA_MELDING;
import static no.nav.syfo.domain.enums.FellesformatType.UKJENT_APPREC;
import static no.nav.syfo.domain.enums.MeldingLoggType.INNKOMMENDE_APPREC;

@Service
@Slf4j
public class FellesformatRuter {
    private AppRecService appRecService;
    private SyfoMeldingService syfoMeldingService;
    private MottakQueueEia2MeldingerProvider mottakQueueEia2MeldingerProvider;
    private MottakQueueEbrevKvitteringProvider mottakQueueEbrevKvitteringProvider;

    private MeldingRepository meldingRepository;
    private MeldingLoggRepository meldingLoggRepository;

    public FellesformatRuter(AppRecService appRecService,
                             SyfoMeldingService syfoMeldingService,
                             MottakQueueEia2MeldingerProvider mottakQueueEia2MeldingerProvider,
                             MottakQueueEbrevKvitteringProvider mottakQueueEbrevKvitteringProvider,
                             MeldingRepository meldingRepository,
                             MeldingLoggRepository meldingLoggRepository) {
        this.appRecService = appRecService;
        this.syfoMeldingService = syfoMeldingService;
        this.mottakQueueEia2MeldingerProvider = mottakQueueEia2MeldingerProvider;
        this.mottakQueueEbrevKvitteringProvider = mottakQueueEbrevKvitteringProvider;
        this.meldingRepository = meldingRepository;
        this.meldingLoggRepository = meldingLoggRepository;
    }

    public void evaluer(Fellesformat fellesformat) {
        if (fellesformat.erAppRec()) {
            evaluerAppRec(fellesformat);
        } else {
            evaluerHodemelding(fellesformat);
        }
    }

    private void evaluerAppRec(Fellesformat fellesformat) {
        FellesformatType type = identifiserAppRec(fellesformat);
        log.info("Mottatt apprec av type {}", type);
        switch (type) {
            case SYFO_MELDING:
                fellesformat.getAppRecStream().forEach(appRecService::registrerMottattAppRec);
                fellesformat.getAppRecStream().forEach(appRec ->
                        meldingLoggRepository.loggMelding(
                                fellesformat.getMessage(),
                                meldingRepository.finnIdForMeldingId(appRec.originalMessageId()),
                                INNKOMMENDE_APPREC));
                break;
            case UKJENT_APPREC:
                mottakQueueEbrevKvitteringProvider.sendTilEMottak(fellesformat.getMessage());
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
        return meldingRepository.finnMeldingstypeForMeldingIdSet(
                fellesformat
                        .getAppRecStream()
                        .map(AppRec::originalMessageId)
                        .collect(toSet()))
                .orElse(UKJENT_APPREC);
    }

    private FellesformatType identifiserHodemelding(Fellesformat fellesformat) {
        return meldingRepository.finnMeldingstypeForDokumentIdSet(
                concat(
                        fellesformat
                                .getHodemeldingStream()
                                .flatMap(Hodemelding::getDokIdNotatStream),
                        fellesformat
                                .getHodemeldingStream()
                                .flatMap(Hodemelding::getDokIdForesporselStream))
                        .collect(toSet()))
                .orElse(EIA_MELDING);
    }
}
