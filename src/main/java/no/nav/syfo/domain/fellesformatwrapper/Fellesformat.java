package no.nav.syfo.domain.fellesformatwrapper;

import no.kith.xmlstds.msghead._2006_05_24.XMLMsgHead;
import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.apprecwrapper.AppRec1_0;
import no.nav.syfo.domain.apprecwrapper.AppRec1_1;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.trygdeetaten.xml.eiff._1.XMLEIFellesformat;
import no.trygdeetaten.xml.eiff._1.XMLMottakenhetBlokk;

import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Fellesformat {
    private TextMessage textMessage;
    private XMLEIFellesformat fellesformat;

    private List<XMLMottakenhetBlokk> mottakenhetBlokkListe;

    private List<Hodemelding> hodemeldingListe;
    private List<AppRec> appRecListe;

    public Fellesformat(TextMessage textMessage, XMLEIFellesformat fellesformat) {
        this.textMessage = textMessage;
        this.fellesformat = fellesformat;

        this.mottakenhetBlokkListe = new ArrayList<>();

        this.hodemeldingListe = new ArrayList<>();
        this.appRecListe = new ArrayList<>();

        fellesformat.getAny().forEach(melding -> {
            if (melding instanceof XMLMsgHead) {
                hodemeldingListe.add(new Hodemelding((XMLMsgHead) melding));
            } else if (melding instanceof no.kith.xmlstds.apprec._2004_11_21.XMLAppRec) {
                appRecListe.add(new AppRec1_0((no.kith.xmlstds.apprec._2004_11_21.XMLAppRec) melding));
            } else if (melding instanceof no.kith.xmlstds.apprec._2012_02_15.XMLAppRec) {
                appRecListe.add(new AppRec1_1((no.kith.xmlstds.apprec._2012_02_15.XMLAppRec) melding));
            } else if (melding instanceof XMLMottakenhetBlokk) {
                mottakenhetBlokkListe.add((XMLMottakenhetBlokk) melding);
            }
        });
    }

    public Stream<Hodemelding> getHodemeldingStream() {
        return hodemeldingListe.stream();
    }

    public Stream<AppRec> getAppRecStream() {
        return appRecListe.stream();
    }

    public TextMessage getTextMessage() {
        return textMessage;
    }
}
