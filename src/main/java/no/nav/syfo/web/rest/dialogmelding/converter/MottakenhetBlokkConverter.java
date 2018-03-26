package no.nav.syfo.web.rest.dialogmelding.converter;

import no.nav.syfo.web.rest.dialogmelding.model.RSHodemelding;
import no.nav.xml.eiff._2.ObjectFactory;
import no.nav.xml.eiff._2.XMLMottakenhetBlokk;

public class MottakenhetBlokkConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private RSHodemelding rsHodemelding;

    private XMLMottakenhetBlokk xmlMottakenhetBlokk;

    public MottakenhetBlokkConverter(RSHodemelding rsHodemelding) {
        this.rsHodemelding = rsHodemelding;
    }

    public XMLMottakenhetBlokk getMottakenhetBlokk() {
        ensureMottakenhetBlokk();
        return xmlMottakenhetBlokk;
    }

    private void ensureMottakenhetBlokk() {
        if (this.xmlMottakenhetBlokk == null) {
            this.xmlMottakenhetBlokk = FACTORY.createXMLMottakenhetBlokk()
//                    .withEdiLoggId()
//                    .withAvsender()
//                    .withEbXMLSamtaleId()
//                    .withMottaksId()
//                    .withMeldingsType()
//                    .withAvsenderRef()
//                    .withAvsenderFnrFraDigSignatur()
//                    .withMottattDatotid()
//                    .withOrgNummer()
//                    .withAvsenderOrgNrFraDigSignatur()
                    .withPartnerReferanse(rsHodemelding.getMeldingInfo().getMottaker().getPartnerId())
//                    .withHerIdentifikator()
                    .withEbRole("Saksbehandler")
                    .withEbService("ForesporselFraSaksbehandler")
                    .withEbAction("Foresporsel");
        }
    }
}
