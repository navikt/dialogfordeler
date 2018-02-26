package no.nav.syfo.service;

import no.nav.syfo.domain.enums.FellesformatType;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.provider.mq.MottakQueueEia2MeldingerProvider;
import no.nav.syfo.repository.MeldingIdRepository;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toSet;
import static no.nav.syfo.domain.enums.FellesformatType.EIA_MELDING;

@Service
public class FellesformatRuter {
    private AppRecService appRecService;
    private HodemeldingService hodemeldingService;
    private MottakQueueEia2MeldingerProvider mottakQueueEia2MeldingerProvider;
    private MeldingIdRepository meldingIdRepository;

    public FellesformatRuter(AppRecService appRecService,
                             HodemeldingService hodemeldingService,
                             MottakQueueEia2MeldingerProvider mottakQueueEia2MeldingerProvider,
                             MeldingIdRepository meldingIdRepository) {
        this.appRecService = appRecService;
        this.hodemeldingService = hodemeldingService;
        this.mottakQueueEia2MeldingerProvider = mottakQueueEia2MeldingerProvider;
        this.meldingIdRepository = meldingIdRepository;
    }

    public void evaluer(Fellesformat fellesformat) {
        switch (identifiser(fellesformat)) {
            case SYFO_APPREC:
                fellesformat.getAppRecStream().forEach(appRecService::doSomething);
                break;
            case SYFO_HODEMELDING:
                fellesformat.getHodemeldingStream().forEach(hodemeldingService::doSomething);
                break;
            case EIA_MELDING:
                mottakQueueEia2MeldingerProvider.sendTilEia(fellesformat);
                break;
        }
    }

    private FellesformatType identifiser(Fellesformat fellesformat) {
        return meldingIdRepository.finnMeldingstype(fellesformat.meldingIdStream().collect(toSet())).orElse(EIA_MELDING);
    }
}
