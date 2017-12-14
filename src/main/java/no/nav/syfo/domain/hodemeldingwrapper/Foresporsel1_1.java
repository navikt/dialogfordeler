package no.nav.syfo.domain.hodemeldingwrapper;

import lombok.Data;
import lombok.NonNull;
import no.kith.xmlstds.dialog._2013_01_23.XMLForesporsel;

@Data
public class Foresporsel1_1 implements Foresporsel {
    @NonNull
    private XMLForesporsel xmlForesporsel;
}
