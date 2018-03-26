package no.nav.syfo.web.rest.dialogmelding.converter;

import no.kith.xmlstds.msghead._2006_05_24.ObjectFactory;
import no.kith.xmlstds.msghead._2006_05_24.XMLReceiver;
import no.nav.syfo.web.rest.dialogmelding.model.RSMottaker;

public class MottakerConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private RSMottaker rsMottaker;

    private XMLReceiver receiver;

    public MottakerConverter(RSMottaker rsMottaker) {
        this.rsMottaker = rsMottaker;
    }

    public XMLReceiver getReceiver() {
        ensureOrganisation();
        return receiver;
    }

    private void ensureOrganisation() {
        if (this.receiver == null) {
            this.receiver = FACTORY.createXMLReceiver()
                    .withOrganisation(FACTORY.createXMLOrganisation()
                            .withOrganisationName(rsMottaker.getNavn())
                            .withIdent(FACTORY.createXMLIdent()
                                    .withId(rsMottaker.getHerId())
                                    .withTypeId(FACTORY.createXMLCV()
                                            .withDN("Identifikator fra Helsetjenesteenhetsregisteret (HER-id)")
                                            .withS("2.16.578.1.12.4.1.1.9051")
                                            .withV("HER")))
                            .withIdent(FACTORY.createXMLIdent()
                                    .withId(rsMottaker.getOrgnummer())
                                    .withTypeId(FACTORY.createXMLCV()
                                            .withDN("Organisasjonsnummeret i Enhetsregisteret")
                                            .withS("2.16.578.1.12.4.1.1.9051")
                                            .withV("ENH")))
                            .withAddress(FACTORY.createXMLAddress()
                                    .withStreetAdr(rsMottaker.getAdresse())
                                    .withPostalCode(rsMottaker.getPostnummer())
                                    .withCity(rsMottaker.getPoststed()))
                            .withHealthcareProfessional(FACTORY.createXMLHealthcareProfessional()
                                    .withRoleToPatient(FACTORY.createXMLCV()
                                            .withV("4"))
                                    .withFamilyName(rsMottaker.getBehandler().getEtternavn())
                                    .withMiddleName(rsMottaker.getBehandler().getMellomnavn())
                                    .withGivenName(rsMottaker.getBehandler().getFornavn())
                                    .withIdent(FACTORY.createXMLIdent()
                                            .withId(rsMottaker.getBehandler().getFnr())
                                            .withTypeId(FACTORY.createXMLCV()
                                                    .withDN("Fødselsnummer Norsk fødselsnummer")
                                                    .withS("2.16.578.1.12.4.1.1.8116")
                                                    .withV("FNR")))
                                    .withIdent(FACTORY.createXMLIdent()
                                            .withId(rsMottaker.getBehandler().getHprId())
                                            .withTypeId(FACTORY.createXMLCV()
                                                    .withDN("HPR-nummer")
                                                    .withS("2.16.578.1.12.4.1.1.8116")
                                                    .withV("HPR")))));
        }
    }
}
