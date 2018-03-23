package no.nav.syfo.provider.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

import static no.nav.syfo.util.JmsUtil.messageCreator;

/**
 * QA.Q414.IU03_EBREV_KVITTERING
 */
@Component
@Slf4j
public class MottakQueueEbrevKvittering {
    private JmsTemplate jmsMottakQueueEbrevKvittering;
    private boolean leggMeldingerPaKo;

    public MottakQueueEbrevKvittering(JmsTemplate jmsMottakQueueEbrevKvittering,
                                      @Value("${toggle.legg.meldinger.pa.ko:false}")
                                                boolean leggMeldingerPaKo) {
        this.jmsMottakQueueEbrevKvittering = jmsMottakQueueEbrevKvittering;
        this.leggMeldingerPaKo = leggMeldingerPaKo;
    }

    private final Consumer<String> jmsSender = message -> jmsMottakQueueEbrevKvittering.send(messageCreator(message));

    public void sendTilEMottak(@NotNull String message) {
        if (leggMeldingerPaKo) {
            log.info("Apprec til eMottak: Sender");
            jmsSender.accept(message);
        } else {
            log.info("Apprec til eMottak: Sending deaktivert");
        }
    }
}
