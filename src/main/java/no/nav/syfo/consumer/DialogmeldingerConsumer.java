package no.nav.syfo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
@Slf4j
public class DialogmeldingerConsumer {
    @JmsListener(id = "dialogmeldinger_listener", containerFactory = "jmsListenerContainerFactory", destination = "dialogmeldingerQueue")
    public void listen(Object message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            final String text = textMessage.getText();
            log.info("Lest melding {} med innhold: {}", textMessage.getJMSCorrelationID(), text);
        } catch (JMSException e) {
            log.error("Feil ved lesing av melding", e);
        }
    }
}
