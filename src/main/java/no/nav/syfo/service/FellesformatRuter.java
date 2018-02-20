package no.nav.syfo.service;

import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.provider.mq.MottakQueueEia2MeldingerProvider;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class FellesformatRuter {
    private HodemeldingService hodemeldingService;
    private AppRecService appRecService;
    private MottakQueueEia2MeldingerProvider mottakQueueEia2MeldingerProvider;

    public void evaluer(Fellesformat fellesformat) {
        if (fellesformat.erSyfoAppRec()) {
            fellesformat.getAppRecStream().forEach(appRecService::doSomething);
        } else if (fellesformat.erSyfoHodemelding()) {
            fellesformat.getHodemeldingStream().forEach(hodemeldingService::doSomething);
        } else {
            mottakQueueEia2MeldingerProvider.sendTilEia(fellesformat);
        }
    }

    @Inject
    public void setHodemeldingService(HodemeldingService hodemeldingService) {
        this.hodemeldingService = hodemeldingService;
    }

    @Inject
    public void setAppRecService(AppRecService appRecService) {
        this.appRecService = appRecService;
    }

    @Inject
    public void setMottakQueueEia2MeldingerProvider(MottakQueueEia2MeldingerProvider mottakQueueEia2MeldingerProvider) {
        this.mottakQueueEia2MeldingerProvider = mottakQueueEia2MeldingerProvider;
    }
}
