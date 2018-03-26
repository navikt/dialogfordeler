package no.nav.syfo.web.rest.dialogmelding.converter;

import no.kith.xmlstds.msghead._2006_05_24.ObjectFactory;
import no.kith.xmlstds.msghead._2006_05_24.XMLPatient;
import no.nav.syfo.web.rest.dialogmelding.model.RSPasient;

public class PasientConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private RSPasient rsPasient;

    private XMLPatient patient;

    public PasientConverter(RSPasient rsPasient) {
        this.rsPasient = rsPasient;
    }

    public XMLPatient getPatient() {
        ensurePasient();
        return patient;
    }

    private void ensurePasient() {
        if (this.patient == null) {
            patient = FACTORY.createXMLPatient()
                    .withFamilyName(rsPasient.getEtternavn())
                    .withMiddleName(rsPasient.getMellomnavn())
                    .withGivenName(rsPasient.getFornavn())
                    .withIdent(FACTORY.createXMLIdent()
                            .withId(rsPasient.getFnr())
                            .withTypeId(FACTORY.createXMLCV()
                                    .withDN("Fødselsnummer Norsk fødselsnummer")
                                    .withS("2.16.578.1.12.4.1.1.8116")
                                    .withV("FNR")));
        }
    }
}
