package no.nav.syfo.web.rest.dialogmelding.converter;

import no.kith.xmlstds.dialog._2006_10_11.ObjectFactory;
import no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding;
import no.nav.syfo.web.rest.dialogmelding.model.RSDialogmelding;

import java.util.UUID;

public class DialogmeldingConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();
    private static final no.kith.xmlstds.ObjectFactory FELLES_FACTORY = new no.kith.xmlstds.ObjectFactory();

    private RSDialogmelding rsDialogmelding;

    private XMLDialogmelding dialogmelding;

    public DialogmeldingConverter(RSDialogmelding rsDialogmelding) {
        this.rsDialogmelding = rsDialogmelding;
    }

    public XMLDialogmelding getDialogmelding() {
        ensureDialogmelding();
        return dialogmelding;
    }

    private void ensureDialogmelding() {
        if (this.dialogmelding == null) {
            this.dialogmelding = FACTORY.createXMLDialogmelding()
                    .withForesporsel(FACTORY.createXMLForesporsel()
                            .withTypeForesp(FELLES_FACTORY.createXMLCV()
                                    .withDN("Endring dialogmøte 3")
                                    .withS("2.16.578.1.12.4.1.1.8125")
                                    .withV("4"))
                            .withSporsmal(rsDialogmelding.getSporsmal())
                            .withDokIdForesp(UUID.randomUUID().toString())
                            .withRollerRelatertNotat(FACTORY.createXMLRollerRelatertNotat()
                                    .withRolleNotat(FELLES_FACTORY.createXMLCV()
                                            .withS("2.16.578.1.12.4.1.1.9057")
                                            .withV("1"))
                                    .withPerson(FACTORY.createXMLPerson()
                                            .withFamilyName(rsDialogmelding.getRoller().getPersonEtternavn())
                                            .withMiddleName(rsDialogmelding.getRoller().getPersonMellomnavn())
                                            .withGivenName(rsDialogmelding.getRoller().getPersonFornavn()))));
        }
    }
}
