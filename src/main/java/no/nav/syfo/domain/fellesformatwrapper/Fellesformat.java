package no.nav.syfo.domain.fellesformatwrapper;

import no.kith.xmlstds.msghead._2006_05_24.XMLMsgHead;
import no.nav.syfo.domain.apprecwrapper.AppRec;
import no.nav.syfo.domain.apprecwrapper.AppRec1_0;
import no.nav.syfo.domain.apprecwrapper.AppRec1_1;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.xml.eiff._2.XMLEIFellesformat;
import no.nav.xml.eiff._2.XMLMottakenhetBlokk;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Fellesformat {
    private String message;
    private XMLEIFellesformat fellesformat;

    private List<XMLMottakenhetBlokk> mottakenhetBlokkListe;

    private List<Hodemelding> hodemeldingListe;
    private List<AppRec> appRecListe;

    public Fellesformat(String message, XMLEIFellesformat fellesformat) {
        this(fellesformat);
        this.message = message;
    }

    public Fellesformat(XMLEIFellesformat fellesformat) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public XMLEIFellesformat getEIFellesformat() {
        return fellesformat;
    }

    public boolean erHodemelding() {
        return getHodemeldingStream().count() > 0;
    }

    public boolean erAppRec() {
        return getAppRecStream().count() > 0;
    }
}
