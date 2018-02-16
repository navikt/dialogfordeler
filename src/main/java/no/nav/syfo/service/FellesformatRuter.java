package no.nav.syfo.service;

import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.EiaQueueMottakInboundProvider;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.singletonList;

@Service
public class FellesformatRuter {
    private HodemeldingService hodemeldingService;
    private AppRecService appRecService;
    private EiaQueueMottakInboundProvider eiaQueueMottakInboundProvider;

    public void evaluer(Fellesformat fellesformat) {
        if (erSyfomelding(fellesformat)) {
            fellesformat.getAppRecStream().forEach(appRecService::doSomething);
            fellesformat.getHodemeldingStream().forEach(hodemeldingService::doSomething);
        } else {
            eiaQueueMottakInboundProvider.sendTilEia(fellesformat);
        }
    }

    private boolean erSyfomelding(Fellesformat fellesformat) {
        return erSyfoHodemelding(fellesformat) || erSyfoAppRec(fellesformat);
    }

    private boolean erSyfoHodemelding(Fellesformat fellesformat) {
        //TODO: Må sjekke en eller annen liste...
        List<String> enEllerAnnenListe = singletonList("dokidnotat");
        return fellesformat
                .getHodemeldingStream()
                .flatMap(Hodemelding::getDokIdNotatStream)
                .anyMatch(enEllerAnnenListe::contains);
    }

    private boolean erSyfoAppRec(Fellesformat fellesformat) {
        //TODO: Må sjekke en eller annen liste...
        List<String> enEllerAnnenListe = singletonList("enellera-nnen-uuid-elle-rnoesåntnoe!");
        return fellesformat
                .getAppRecStream()
                .map(AppRec::originalMessageId)
                .anyMatch(enEllerAnnenListe::contains);
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
