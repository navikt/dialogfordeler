package no.nav.syfo.domain.hodemeldingwrapper;

import lombok.Data;
import no.kith.xmlstds.msghead._2006_05_24.XMLDocument;
import no.kith.xmlstds.msghead._2006_05_24.XMLMsgHead;
import no.nav.syfo.util.JAXB;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Stream.of;

@Data
public class Hodemelding {
    private TextMessage textMessage;
    private XMLMsgHead msgHead;
    private List<Dokument> dokumentListe;

    public Hodemelding(TextMessage textMessage) throws JMSException {
        this.textMessage = textMessage;
        this.msgHead = JAXB.unmarshalHodemelding(textMessage.getText());
        this.dokumentListe = new ArrayList<>();

        getDocuments().map(Dokument::new).forEach(dokumentListe::add);
    }

    private Stream<XMLDocument> getDocuments() {
        return of(msgHead)
                .map(XMLMsgHead::getDocument)
                .flatMap(Collection::stream);
    }

    public boolean erForesporsel() {
        return dokumentListe.stream().anyMatch(Dokument::erForesporsel);
    }

    public boolean erNotat() {
        return dokumentListe.stream().anyMatch(Dokument::erNotat);
    }

    public boolean harVedlegg() {
        return dokumentListe.stream().anyMatch(Dokument::harVedlegg);
    }

    public Stream<String> getDokIdNotatStream() {
        return dokumentListe.stream().flatMap(Dokument::getDokIdNotatListe);
    }
}