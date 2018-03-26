package no.nav.syfo.web.rest.dialogmelding.converter;

import no.kith.xmlstds.msghead._2006_05_24.ObjectFactory;
import no.kith.xmlstds.msghead._2006_05_24.XMLDocument;
import no.nav.syfo.web.rest.dialogmelding.model.RSVedlegg;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DokumentVedleggConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();
    private static final no.kith.xmlstds.base64container.ObjectFactory VEDLEGG_FACTORY = new no.kith.xmlstds.base64container.ObjectFactory();

    private RSVedlegg rsVedlegg;

    private XMLDocument document;

    public DokumentVedleggConverter(RSVedlegg rsVedlegg) {
        this.rsVedlegg = rsVedlegg;
    }

    public XMLDocument getDocument() {
        ensureDocument();
        return document;
    }

    private void ensureDocument() {
        if (this.document == null) {
            this.document = FACTORY.createXMLDocument()
                    .withDocumentConnection(FACTORY.createXMLCS()
                            .withDN("Vedlegg")
                            .withV("V"))
                    .withRefDoc(FACTORY.createXMLRefDoc()
                            .withIssueDate(FACTORY.createXMLTS()
                                    .withV(LocalDate.now().format(DateTimeFormatter.ISO_DATE)))
                            .withMsgType(FACTORY.createXMLCS()
                                    .withDN("Vedlegg")
                                    .withV("A"))
                            .withMimeType("application/pdf")
                            .withContent(FACTORY.createXMLRefDocContent()
                                    .withAny(VEDLEGG_FACTORY.createXMLBase64Container()
                                            .withValue(rsVedlegg.getVedlegg()))));
        }
    }
}
