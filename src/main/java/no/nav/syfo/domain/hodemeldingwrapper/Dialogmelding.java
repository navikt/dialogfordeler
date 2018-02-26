package no.nav.syfo.domain.hodemeldingwrapper;

import java.util.stream.Stream;

public interface Dialogmelding {
    enum Versjon {
        _1_0, _1_1
    }

    Versjon versjon();

    boolean erForesporsel();

    boolean erNotat();

    Stream<String> getDokIdNotatStream();
}
