package no.nav.syfo.service;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.repository.AppRecRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppRecService {
    private AppRecRepository appRecRepository;

    public AppRecService(AppRecRepository appRecRepository) {
        this.appRecRepository = appRecRepository;
    }

    public boolean registrerMottattAppRec(AppRec appRec) {
        String meldingId = appRec.originalMessageId();

        log.info("Mottatt AppRec med meldingId {} og statustekst {}", meldingId, appRec.statustekst());

        return appRecRepository.registrerMottattAppRec(meldingId);
    }
}
