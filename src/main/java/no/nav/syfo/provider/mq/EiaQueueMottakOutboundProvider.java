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
 * QA.T414.IU03_KVITTERING
 */
@Component
@Slf4j
public class EiaQueueMottakOutboundProvider {
    private JmsTemplate jmsEiaQueueMottakOutbound;

    private final Consumer<Message> jmsSender = message -> jmsEiaQueueMottakOutbound.send(messageCreator(message));

    public void sendTilEMottak(Hodemelding hodemelding) {

    }

    @Inject
    public void setJmsEiaQueueMottakOutbound(JmsTemplate jmsEiaQueueMottakOutbound) {
        this.jmsEiaQueueMottakOutbound = jmsEiaQueueMottakOutbound;
    }
}