package no.nav.syfo.domain.hodemeldingwrapper;

import lombok.Data;
import no.kith.xmlstds.base64container.XMLBase64Container;
import no.kith.xmlstds.msghead._2006_05_24.XMLDocument;
import no.kith.xmlstds.msghead._2006_05_24.XMLRefDoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Stream.of;

@Data
public class Dokument {
    private List<Dialogmelding> dialogmeldingListe;
    private List<Vedlegg> vedleggListe;

    public Dokument(XMLDocument xmlDocument) {
        this.dialogmeldingListe = new ArrayList<>();
        this.vedleggListe = new ArrayList<>();

        getContent(xmlDocument).forEach(o -> {
            if (o instanceof no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding) {
                dialogmeldingListe.add(new Dialogmelding1_0((no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding) o));
            } else if (o instanceof no.kith.xmlstds.dialog._2013_01_23.XMLDialogmelding) {
                dialogmeldingListe.add(new Dialogmelding1_1((no.kith.xmlstds.dialog._2013_01_23.XMLDialogmelding) o));
            } else if (o instanceof XMLBase64Container) {
                vedleggListe.add(new Vedlegg((XMLBase64Container) o));
            }
        });
    }

    private Stream<Object> getContent(XMLDocument xmlDocument) {
        return of(xmlDocument)
                .map(XMLDocument::getRefDoc)
                .map(XMLRefDoc::getContent)
                .map(XMLRefDoc.Content::getAny)
                .flatMap(Collection::stream);
    }

    public boolean erForesporsel() {
        return dialogmeldingListe.stream().anyMatch(Dialogmelding::erForesporsel);
    }

    public boolean erNotat() {
        return dialogmeldingListe.stream().anyMatch(Dialogmelding::erNotat);
    }

    public boolean harVedlegg(){
        return !vedleggListe.isEmpty();
    }

    public Stream<String> getDokIdForesporselStream() {
        return dialogmeldingListe.stream().flatMap(Dialogmelding::getDokIdForesporselStream);
    }

    public Stream<String> getDokIdNotatStream() {
        return dialogmeldingListe.stream().flatMap(Dialogmelding::getDokIdNotatStream);
    }
}
