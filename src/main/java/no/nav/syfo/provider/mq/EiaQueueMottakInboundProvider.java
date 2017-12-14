package no.nav.syfo.provider.mq;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import no.nav.syfo.props.ToggleProperties;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Message;
import java.util.function.Consumer;

import static java.util.Optional.of;
import static no.nav.syfo.util.JmsUtil.messageCreator;

@Component
@Slf4j
public class EiaQueueMottakInboundProvider {
    private JmsTemplate jmsEiaQueueMottakInbound;
    private ToggleProperties toggleProperties;

    private final Consumer<Message> jmsSender = message -> jmsEiaQueueMottakInbound.send(messageCreator(message));

    public void sendTilEia(Hodemelding hodemelding) {
        log.info("Sendt til eia");
        if (toggleProperties.isLeggMeldingerPaKo()) {
            of(hodemelding)
                    .map(Hodemelding::getTextMessage)
                    .ifPresent(jmsSender);
        }
    }

    @Inject
    public void setJmsEiaQueueMottakInbound(JmsTemplate jmsEiaQueueMottakInbound) {
        this.jmsEiaQueueMottakInbound = jmsEiaQueueMottakInbound;
    }

    @Inject
    public void setToggleProperties(ToggleProperties toggleProperties) {
        this.toggleProperties = toggleProperties;
    }
}
