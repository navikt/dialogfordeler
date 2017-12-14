package no.nav.syfo.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.exception.DialogmeldingInboundException;
import no.nav.syfo.service.HodemeldingRuter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import static java.util.Optional.ofNullable;
import static no.nav.modig.common.MDCOperations.*;

@Component
@Slf4j
public class DialogmeldingerConsumer {
    private HodemeldingRuter hodemeldingRuter;

    @JmsListener(id = "dialogmeldinger_listener", containerFactory = "jmsListenerContainerFactory", destination = "dialogmeldingerQueue")
    public void listen(Object message) {
        putToMDC(MDC_CALL_ID, generateCallId());
        TextMessage textMessage = (TextMessage) message;
        try {
            ofNullable(textMessage.getStringProperty("callId")).ifPresent(callId -> putToMDC(MDC_CALL_ID, callId));
            hodemeldingRuter.evaluer(new Hodemelding(textMessage));
        } catch (JMSException e) {
            log.error("Feil ved lesing av melding", e);
            throw new DialogmeldingInboundException("Feil ved lesing av melding", e);
        }
    }

    @Inject
    public void setHodemeldingRuter(HodemeldingRuter hodemeldingRuter) {
        this.hodemeldingRuter = hodemeldingRuter;
    }
}
