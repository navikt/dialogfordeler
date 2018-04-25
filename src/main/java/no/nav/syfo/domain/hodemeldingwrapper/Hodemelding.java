package no.nav.syfo.domain.hodemeldingwrapper;

import lombok.Data;
import no.kith.xmlstds.msghead._2006_05_24.XMLDocument;
import no.kith.xmlstds.msghead._2006_05_24.XMLMsgHead;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Stream.of;

@Data
public class Hodemelding {
    private XMLMsgHead msgHead;
    private List<Dokument> dokumentListe;

    public Hodemelding(XMLMsgHead msgHead) {
        this.msgHead = msgHead;
        this.dokumentListe = new ArrayList<>();

        getXMLDocumentStream().map(Dokument::new).forEach(dokumentListe::add);
    }

    private Stream<XMLDocument> getXMLDocumentStream() {
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

    public String getMessageId() {
        return msgHead.getMsgInfo().getMsgId();
    }

    public Stream<String> getDokIdForesporselStream() {
        return dokumentListe.stream().flatMap(Dokument::getDokIdForesporselStream);
    }

    public Stream<String> getDokIdNotatStream() {
        return dokumentListe.stream().flatMap(Dokument::getDokIdNotatStream);
    }
}