package no.nav.syfo.domain.hodemeldingwrapper;

import lombok.Data;
import lombok.NonNull;
import no.kith.xmlstds.dialog._2006_10_11.XMLForesporsel;

@Data
public class Foresporsel1_0 implements Foresporsel {
    @NonNull
    private XMLForesporsel xmlForesporsel;

    @Override
    public String getDokIdForesp() {
        return xmlForesporsel.getDokIdForesp();
    }
}
