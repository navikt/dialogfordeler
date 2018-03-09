package no.nav.syfo.provider.mq;

import io.prometheus.client.Counter;
import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.function.Consumer;

import static java.util.Optional.of;
import static no.nav.syfo.util.JmsUtil.messageCreator;

/**
 * QA.T414.FS06_EIA_MELDINGER
 */
@Component
@Slf4j
public class MottakQueueEia2MeldingerProvider {
    private static final String APPREC_LABEL = "AppRec Eia";
    private static final String HODEMELDING_LABEL = "Hodemelding Eia";
    private static final String ANNEN_MELDING_LABEL = "Annen melding Eia";
    private static final String MELDINGSTYPE_LABELNAME = "type";
    private static final Counter COUNTER = Counter
            .build("mq_send_eia_meldinger", "Meldinger sendt til Eia")
            .labelNames(MELDINGSTYPE_LABELNAME)
            .register();

    private JmsTemplate jmsMottakQueueEia2Meldinger;
    private boolean leggMeldingerPaKo;

    public MottakQueueEia2MeldingerProvider(JmsTemplate jmsMottakQueueEia2Meldinger,
                                            @Value("${TOGGLE_LEGG_MELDINGER_PA_KO:true}")
                                                    boolean leggMeldingerPaKo) {
        this.jmsMottakQueueEia2Meldinger = jmsMottakQueueEia2Meldinger;
        this.leggMeldingerPaKo = leggMeldingerPaKo;
    }

    private final Consumer<String> jmsSender = message -> jmsMottakQueueEia2Meldinger.send(messageCreator(message));

    public void sendTilEia(Fellesformat fellesformat) {
        String label;
        if (fellesformat.erAppRec()) {
            log.info("AppRec til eia: " + (leggMeldingerPaKo ? "Sender" : "Sending deaktivert"));
            label = APPREC_LABEL;
        } else if (fellesformat.erHodemelding()) {
            log.info("Hodemelding til eia: " + (leggMeldingerPaKo ? "Sender" : "Sending deaktivert"));
            label = HODEMELDING_LABEL;
        } else {
            log.info("ukjent melding til eia: " + (leggMeldingerPaKo ? "Sender" : "Sending deaktivert"));
            label = ANNEN_MELDING_LABEL;
        }

        if (leggMeldingerPaKo) {
            of(fellesformat)
                    .map(Fellesformat::getMessage)
                    .ifPresent(jmsSender);
        }
        COUNTER.labels(label).inc();
    }

    @Inject
    public void setJmsMottakQueueEia2Meldinger(JmsTemplate jmsMottakQueueEia2Meldinger) {
        this.jmsMottakQueueEia2Meldinger = jmsMottakQueueEia2Meldinger;
    }
}
