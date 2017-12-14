package no.nav.syfo.service;

import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.provider.mq.EiaQueueMottakInboundProvider;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

@Service
public class HodemeldingRuter {
    private HodemeldingService hodemeldingService;
    private EiaQueueMottakInboundProvider eiaQueueMottakInboundProvider;

    public void evaluer(Hodemelding hodemelding) {
        if (erSyfomelding(hodemelding.getDokIdNotatStream())) {
            hodemeldingService.doSomething(hodemelding);
        } else {
            eiaQueueMottakInboundProvider.sendTilEia(hodemelding);
        }
    }

    private boolean erSyfomelding(Stream<String> dokIdNotater) {
        //TODO: Må sjekke en eller annen liste...
        List<String> enEllerAnnenListe = singletonList("dokidnotat");
        return dokIdNotater.anyMatch(enEllerAnnenListe::contains);
    }

    @Inject
    public void setHodemeldingService(HodemeldingService hodemeldingService) {
        this.hodemeldingService = hodemeldingService;
    }

    @Inject
    public void setEiaQueueMottakInboundProvider(EiaQueueMottakInboundProvider eiaQueueMottakInboundProvider) {
        this.eiaQueueMottakInboundProvider = eiaQueueMottakInboundProvider;
    }
}
