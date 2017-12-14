package no.nav.syfo.domain.hodemeldingwrapper;

import lombok.Data;
import lombok.NonNull;
import no.kith.xmlstds.dialog._2006_10_11.XMLNotat;

@Data
public class Notat1_0 implements Notat {
    @NonNull
    private XMLNotat xmlNotat;

    @Override
    public String getDokIdNotat() {
        return xmlNotat.getDokIdNotat();
    }
}
