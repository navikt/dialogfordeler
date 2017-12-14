package no.nav.syfo.domain.hodemeldingwrapper;

import java.util.stream.Stream;

public interface Dialogmelding {
    boolean erForesporsel();

    boolean erNotat();

    Stream<String> getDokIdNotatStream();
}
