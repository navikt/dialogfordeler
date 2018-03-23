package no.nav.syfo.service;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SyfoMeldingService {
    public void doSomething(Hodemelding hodemelding) {
        log.info("Sendt til syfo");
        throw new IllegalStateException("Ikke implementert");
    }
}
