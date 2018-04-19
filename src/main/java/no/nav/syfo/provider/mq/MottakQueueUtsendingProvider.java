package no.nav.syfo.provider.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

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
    private boolean leggMeldingerPaKo;

    public MottakQueueUtsendingProvider(JmsTemplate jmsMottakQueueUtsending,
                                        @Value("${toggle.legg.meldinger.pa.ko:false}")
                                                boolean leggMeldingerPaKo) {
        this.jmsMottakQueueUtsending = jmsMottakQueueUtsending;
        this.leggMeldingerPaKo = leggMeldingerPaKo;
    }

    private final Consumer<String> jmsSender = message -> jmsMottakQueueUtsending.send(messageCreator(message));

    public void sendTilEMottak(@NotNull String message) {
        if (leggMeldingerPaKo) {
            log.info("Melding til eMottak: Sender");
            log.info("Melding uten address:\n" + message);
            jmsSender.accept(message);
        } else {
            log.info("Melding til eMottak: Sending deaktivert");
        }
    }
}
