package no.nav.syfo.service;

import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import no.nav.syfo.exception.MeldingInboundException;
import no.nav.syfo.util.JAXB;
import no.trygdeetaten.xml.eiff._1.XMLEIFellesformat;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
public class MeldingRuter {
    private FellesformatRuter fellesformatRuter;

    public void evaluer(TextMessage textMessage) throws JMSException {
        Object melding = JAXB.unmarshalMelding(textMessage.getText());
        if (melding instanceof XMLEIFellesformat) {
            fellesformatRuter.evaluer(new Fellesformat(textMessage, (XMLEIFellesformat) melding));
        } else {
            throw new MeldingInboundException("Melding er ikke Fellesformat");
        }
    }

    @Inject
    public void setFellesformatRuter(FellesformatRuter fellesformatRuter) {
        this.fellesformatRuter = fellesformatRuter;
    }
}
