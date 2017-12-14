package no.nav.syfo.domain.hodemeldingwrapper;

import lombok.Data;
import lombok.NonNull;
import no.kith.xmlstds.dialog._2013_01_23.XMLNotat;

@Data
public class Notat1_1 implements Notat {
    @NonNull
    private XMLNotat xmlNotat;

    @Override
    public String getDokIdNotat() {
        return xmlNotat.getDokIdNotat();
    }
}
