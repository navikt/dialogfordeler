package no.nav.syfo.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.exception.MeldingInboundException;
import no.nav.syfo.service.MeldingRuter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import static java.util.Optional.ofNullable;
import static no.nav.modig.common.MDCOperations.*;

@Component
@Slf4j
public class DialogmeldingerConsumer {
    private MeldingRuter meldingRuter;

    @Transactional
    @JmsListener(id = "dialogmeldinger_listener", containerFactory = "jmsListenerContainerFactory", destination = "dialogmeldingerQueue")
    public void listen(Object message) {
        putToMDC(MDC_CALL_ID, generateCallId());
        TextMessage textMessage = (TextMessage) message;
        try {
            ofNullable(textMessage.getStringProperty("callId")).ifPresent(callId -> putToMDC(MDC_CALL_ID, callId));
            meldingRuter.evaluer(textMessage);
        } catch (JMSException e) {
            log.error("Feil ved lesing av melding", e);
            throw new MeldingInboundException("Feil ved lesing av melding", e);
        }
    }

    @Inject
    public void setMeldingRuter(MeldingRuter meldingRuter) {
        this.meldingRuter = meldingRuter;
    }
}
