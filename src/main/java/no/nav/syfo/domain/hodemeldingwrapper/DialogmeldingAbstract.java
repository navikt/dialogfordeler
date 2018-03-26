package no.nav.syfo.domain.hodemeldingwrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class DialogmeldingAbstract implements Dialogmelding {
    List<Notat> notatListe;
    List<Foresporsel> foresporselListe;

    DialogmeldingAbstract() {
        this.notatListe = new ArrayList<>();
        this.foresporselListe = new ArrayList<>();
    }

    @Override
    public boolean erForesporsel() {
        return !foresporselListe.isEmpty();
    }

    @Override
    public boolean erNotat() {
        return !notatListe.isEmpty();
    }

    @Override
    public Stream<String> getDokIdForespStream() {
        return foresporselListe.stream().map(Foresporsel::getDokIdForesp);
    }

    @Override
    public Stream<String> getDokIdNotatStream() {
        return notatListe.stream().map(Notat::getDokIdNotat);
    }
}
