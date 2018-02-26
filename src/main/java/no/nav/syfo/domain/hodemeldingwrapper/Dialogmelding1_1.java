package no.nav.syfo.domain.hodemeldingwrapper;

import static no.nav.syfo.domain.hodemeldingwrapper.Dialogmelding.Versjon._1_1;

public class Dialogmelding1_1 extends DialogmeldingAbstract {

    public Dialogmelding1_1(no.kith.xmlstds.dialog._2013_01_23.XMLDialogmelding dialogmelding1_1) {
        super();

        dialogmelding1_1.getNotat().stream().map(Notat1_1::new).forEach(notatListe::add);
        dialogmelding1_1.getForesporsel().stream().map(Foresporsel1_1::new).forEach(foresporselListe::add);
    }

    @Override
    public Versjon versjon() {
        return _1_1;
    }
}
