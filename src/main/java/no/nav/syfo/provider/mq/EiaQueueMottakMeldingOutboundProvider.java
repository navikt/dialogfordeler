package no.nav.syfo.provider.mq;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Message;
import java.util.function.Consumer;

import static no.nav.syfo.util.JmsUtil.messageCreator;

/**
 * QA.Q414.IU03_UTSENDING
 */
@Component
@Slf4j
public class EiaQueueMottakMeldingOutboundProvider {
    private JmsTemplate jmsEiaQueueMottakMeldingOutbound;

    private final Consumer<Message> jmsSender = message -> jmsEiaQueueMottakMeldingOutbound.send(messageCreator(message));

    public void sendTilEMottak(Hodemelding hodemelding) {
    }

    @Inject
    public void setJmsEiaQueueMottakMeldingOutbound(JmsTemplate jmsEiaQueueMottakMeldingOutbound) {
        this.jmsEiaQueueMottakMeldingOutbound = jmsEiaQueueMottakMeldingOutbound;
    }
}
