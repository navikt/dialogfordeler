package no.nav.syfo.service;

import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.provider.mq.EiaQueueMottakInboundProvider;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class FellesformatRuter {
    private HodemeldingService hodemeldingService;
    private AppRecService appRecService;
    private EiaQueueMottakInboundProvider eiaQueueMottakInboundProvider;

    public void evaluer(Fellesformat fellesformat) {
        if (fellesformat.erSyfoAppRec()) {
            fellesformat.getAppRecStream().forEach(appRecService::doSomething);
        } else if (fellesformat.erSyfoHodemelding()) {
            fellesformat.getHodemeldingStream().forEach(hodemeldingService::doSomething);
        } else {
            eiaQueueMottakInboundProvider.sendTilEia(fellesformat);
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
    public void setEiaQueueMottakInboundProvider(EiaQueueMottakInboundProvider eiaQueueMottakInboundProvider) {
        this.eiaQueueMottakInboundProvider = eiaQueueMottakInboundProvider;
    }
}
