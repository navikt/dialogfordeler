package no.nav.syfo.web.rest.dialogmelding.converter;

import no.nav.syfo.web.rest.dialogmelding.model.RSHodemelding;
import no.nav.xml.eiff._2.ObjectFactory;
import no.nav.xml.eiff._2.XMLEIFellesformat;

public class FellesformatConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private HodemeldingConverter hodemeldingConverter;
    private MottakenhetBlokkConverter mottakenhetBlokkConverter;

    private XMLEIFellesformat eiFellesformat;

    public FellesformatConverter(RSHodemelding rsHodemelding) {
        this.hodemeldingConverter = new HodemeldingConverter(rsHodemelding);
        this.mottakenhetBlokkConverter = new MottakenhetBlokkConverter(rsHodemelding);
    }

    public XMLEIFellesformat getEiFellesformat() {
        ensureFellesformat();
        return eiFellesformat;
    }

    private void ensureFellesformat() {
        if (this.eiFellesformat == null) {
            this.eiFellesformat = FACTORY.createXMLEIFellesformat()
                    .withAny(hodemeldingConverter.getMsgHead())
                    .withAny(mottakenhetBlokkConverter.getMottakenhetBlokk());
        }
    }
}
