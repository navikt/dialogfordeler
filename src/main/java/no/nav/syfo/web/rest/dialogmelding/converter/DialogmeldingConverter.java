package no.nav.syfo.web.rest.dialogmelding.converter;

import no.kith.xmlstds.dialog._2006_10_11.ObjectFactory;
import no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding;

import java.util.UUID;

public class DialogmeldingConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();
    private static final no.kith.xmlstds.ObjectFactory FELLES_FACTORY = new no.kith.xmlstds.ObjectFactory();

    private XMLDialogmelding dialogmelding;

    public XMLDialogmelding getDialogmelding() {
        ensureDialogmelding();
        return dialogmelding;
    }

    private void ensureDialogmelding() {
        if (this.dialogmelding == null) {
            this.dialogmelding = FACTORY.createXMLDialogmelding()
                    .withNotat(FACTORY.createXMLNotat()
                            .withTemaKodet(FELLES_FACTORY.createXMLCV()
                                    .withDN("Oppfølgingsplan")
                                    .withS("2.16.578.1.12.4.1.1.8127")
                                    .withV("1"))
                            .withTekstNotatInnhold("Åpne PDF-vedlegg")
                            .withDokIdNotat(UUID.randomUUID().toString())
                            .withRollerRelatertNotat(FACTORY.createXMLRollerRelatertNotat()
                                    .withRolleNotat(FELLES_FACTORY.createXMLCV()
                                            .withS("2.16.578.1.12.4.1.1.9057")
                                            .withV("1"))
                                    .withPerson(FACTORY.createXMLPerson())));
        }
    }
}
