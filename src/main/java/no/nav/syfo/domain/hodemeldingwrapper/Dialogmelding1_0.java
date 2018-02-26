package no.nav.syfo.domain.hodemeldingwrapper;

import static no.nav.syfo.domain.hodemeldingwrapper.Dialogmelding.Versjon._1_0;

public class Dialogmelding1_0 extends DialogmeldingAbstract {

    public Dialogmelding1_0(no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding dialogmelding1_0) {
        super();

        dialogmelding1_0.getNotat().stream().map(Notat1_0::new).forEach(notatListe::add);
        dialogmelding1_0.getForesporsel().stream().map(Foresporsel1_0::new).forEach(foresporselListe::add);
    }

    @Override
    public Versjon versjon() {
        return _1_0;
    }
}
