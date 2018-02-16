package no.nav.syfo.service;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppRecService {
    public void doSomething(AppRec appRec) {
        log.info("Mottatt AppRec");
        throw new IllegalStateException("Ikke implementert");
    }
}
