package no.nav.syfo.web.rest.dialogmelding.converter;

import no.kith.xmlstds.msghead._2006_05_24.ObjectFactory;
import no.kith.xmlstds.msghead._2006_05_24.XMLDocument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DokumentDialogmeldingConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private DialogmeldingConverter dialogmeldingConverter;

    private XMLDocument document;

    public DokumentDialogmeldingConverter() {
        this.dialogmeldingConverter = new DialogmeldingConverter();
    }

    public XMLDocument getDocument() {
        ensureDocument();
        return document;
    }

    private void ensureDocument() {
        if (this.document == null) {
            this.document = FACTORY.createXMLDocument()
                    .withDocumentConnection(FACTORY.createXMLCS()
                            .withDN("Hoveddokument")
                            .withV("H"))
                    .withRefDoc(FACTORY.createXMLRefDoc()
                            .withIssueDate(FACTORY.createXMLTS()
                                    .withV(LocalDate.now().format(DateTimeFormatter.ISO_DATE)))
                            .withMsgType(FACTORY.createXMLCS()
                                    .withDN("XML-instans")
                                    .withV("XML"))
                            .withMimeType("text/xml")
                            .withContent(FACTORY.createXMLRefDocContent()
                                    .withAny(dialogmeldingConverter.getDialogmelding())));
        }
    }
}