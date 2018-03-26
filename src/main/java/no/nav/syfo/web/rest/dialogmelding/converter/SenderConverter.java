package no.nav.syfo.web.rest.dialogmelding.converter;

import no.kith.xmlstds.msghead._2006_05_24.ObjectFactory;
import no.kith.xmlstds.msghead._2006_05_24.XMLSender;
import no.nav.syfo.web.rest.dialogmelding.model.RSSender;


public class SenderConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private RSSender rsSender;

    private XMLSender sender;

    public SenderConverter(RSSender rsSender) {
        this.rsSender = rsSender;
    }

    public XMLSender getSender() {
        ensureOrganisation();
        return sender;
    }

    private void ensureOrganisation() {
        if (this.sender == null) {
            this.sender = FACTORY.createXMLSender()
                    .withOrganisation(FACTORY.createXMLOrganisation()
                            .withOrganisationName("NAV")
                            .withIdent(FACTORY.createXMLIdent()
                                    .withId("889640782")
                                    .withTypeId(FACTORY.createXMLCV()
                                            .withDN("Organisasjonsnummeret i Enhetsregisteret")
                                            .withS("2.16.578.1.12.4.1.1.9051")
                                            .withV("ENH")))
                            .withIdent(FACTORY.createXMLIdent()
                                    .withId("79768")
                                    .withTypeId(FACTORY.createXMLCV()
                                            .withDN("Identifikator fra Helsetjenesteenhetsregisteret (HER-id)")
                                            .withS("2.16.578.1.12.4.1.1.9051")
                                            .withV("HER")))
                            .withOrganisation(FACTORY.createXMLOrganisation()
                                    .withOrganisationName(rsSender.getNavn())
                                    .withIdent(FACTORY.createXMLIdent()
                                            .withId(rsSender.getId())
                                            .withTypeId(FACTORY.createXMLCV()
                                                    .withDN("Lokal identifikator for institusjoner")
                                                    .withS("2.16.578.1.12.4.1.1.9051")
                                                    .withV("LIN")))));
        }
    }
}
