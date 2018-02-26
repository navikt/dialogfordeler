package no.nav.syfo.provider.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

import static no.nav.syfo.util.JmsUtil.messageCreator;

/**
 * QA.Q414.IU03_UTSENDING
 */
@Component
@Slf4j
public class MottakQueueUtsendingProvider {
    private JmsTemplate jmsMottakQueueUtsending;

    @Value("${TOGGLE_LEGG_MELDINGER_PA_KO}")
    private boolean leggMeldingerPaKo;

    private final Consumer<String> jmsSender = message -> jmsMottakQueueUtsending.send(messageCreator(message));

    public void sendTilEMottak(@NotNull String message) {
        log.info("Sender melding til eMottak");
        if (leggMeldingerPaKo) {
            jmsSender.accept(message);
        }
    }

    @Inject
    public void setJmsMottakQueueUtsending(JmsTemplate jmsMottakQueueUtsending) {
        this.jmsMottakQueueUtsending = jmsMottakQueueUtsending;
    }
}
