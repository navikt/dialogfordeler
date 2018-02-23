package no.nav.syfo.provider.mq;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Message;
import java.util.function.Consumer;

import static java.util.Optional.of;
import static no.nav.syfo.util.JmsUtil.messageCreator;

/**
 * QA.T414.FS06_EIA_MELDINGER
 */
@Component
@Slf4j
public class MottakQueueEia2MeldingerProvider {
    private JmsTemplate jmsMottakQueueEia2Meldinger;

    @Value("${TOGGLE_LEGG_MELDINGER_PA_KO}")
    private boolean leggMeldingerPaKo;

    private final Consumer<Message> jmsSender = message -> jmsMottakQueueEia2Meldinger.send(messageCreator(message));

    public void sendTilEia(Fellesformat fellesformat) {
        if (fellesformat.erAppRec()) {
            log.info("Sender AppRec til eia");
        } else if (fellesformat.erHodemelding()) {
            log.info("Sender Hodemelding til eia");
        } else {
            log.info("Sender ukjent melding til eia");
        }

        if (leggMeldingerPaKo) {
            of(fellesformat)
                    .map(Fellesformat::getTextMessage)
                    .ifPresent(jmsSender);
        }
    }

    @Inject
    public void setJmsMottakQueueEia2Meldinger(JmsTemplate jmsMottakQueueEia2Meldinger) {
        this.jmsMottakQueueEia2Meldinger = jmsMottakQueueEia2Meldinger;
    }
}