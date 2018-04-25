package no.nav.syfo.web.rest.dialogmelding.converter;

import no.kith.xmlstds.msghead._2006_05_24.ObjectFactory;
import no.kith.xmlstds.msghead._2006_05_24.XMLMsgHead;
import no.nav.syfo.web.rest.dialogmelding.model.RSHodemelding;

public class HodemeldingConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private MeldingInfoConverter meldingInfoConverter;
    private DokumentDialogmeldingConverter dokumentDialogmeldingConverter;
    private DokumentVedleggConverter dokumentVedleggConverter;

    private XMLMsgHead msgHead;

    public HodemeldingConverter(RSHodemelding rsHodemelding) {
        this.meldingInfoConverter = new MeldingInfoConverter(rsHodemelding.getMeldingInfo());
        this.dokumentDialogmeldingConverter = new DokumentDialogmeldingConverter();
        this.dokumentVedleggConverter = new DokumentVedleggConverter(rsHodemelding.getVedlegg());
    }

    public XMLMsgHead getMsgHead() {
        ensureMsgHead();
        return this.msgHead;
    }

    private void ensureMsgHead() {
        if (this.msgHead == null) {
            this.msgHead = FACTORY.createXMLMsgHead()
                    .withMsgInfo(this.meldingInfoConverter.getMsgInfo())
                    .withDocument(this.dokumentDialogmeldingConverter.getDocument())
                    .withDocument(this.dokumentVedleggConverter.getDocument());
        }
    }
}
