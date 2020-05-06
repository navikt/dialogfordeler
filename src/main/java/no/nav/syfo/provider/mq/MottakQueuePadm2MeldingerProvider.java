package no.nav.syfo.provider.mq;

import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.fellesformatwrapper.Fellesformat;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.function.Consumer;

import static io.micrometer.core.instrument.Metrics.counter;
import static java.util.Optional.of;
import static no.nav.syfo.util.JmsUtil.messageCreator;


@Component
@Slf4j
public class MottakQueuePadm2MeldingerProvider {
    private static final String APPREC_LABEL = "AppRec Eia";
    private static final String HODEMELDING_LABEL = "Hodemelding Eia";
    private static final String ANNEN_MELDING_LABEL = "Annen melding Eia";
    private static final String MELDINGSTYPE_LABELNAME = "type";

    private static final Counter COUNTER_APPREC_LABEL = counter("mq_send_Padm2_meldinger", MELDINGSTYPE_LABELNAME, APPREC_LABEL);
    private static final Counter COUNTER_HODEMELDING_LABEL = counter("mq_send_Padm2_meldinger", MELDINGSTYPE_LABELNAME, HODEMELDING_LABEL);
    private static final Counter COUNTER_ANNEN_MELDING_LABEL = counter("mq_send_Padm2_meldinger", MELDINGSTYPE_LABELNAME, ANNEN_MELDING_LABEL);

    private JmsTemplate padm2JmsTemplate;
    private boolean leggMeldingerPaKo;

    public MottakQueuePadm2MeldingerProvider(@Qualifier("Padm2JmsTemplate") JmsTemplate padm2JmsTemplate,
                                            @Value("${toggle.legg.meldinger.pa.ko:true}") boolean leggMeldingerPaKo) {
        this.padm2JmsTemplate = padm2JmsTemplate;
        this.leggMeldingerPaKo = leggMeldingerPaKo;
    }

    private final Consumer<String> jmsSender = message -> padm2JmsTemplate.send(messageCreator(message));

    public void sendTilEia(Fellesformat fellesformat) {
        Counter counter;
        if (fellesformat.erAppRec()) {
            log.info("AppRec til Padm2: " + (leggMeldingerPaKo ? "Sender" : "Sending deaktivert"));
            counter = COUNTER_APPREC_LABEL;
        } else if (fellesformat.erHodemelding()) {
            log.info("Hodemelding til Padm2: " + (leggMeldingerPaKo ? "Sender" : "Sending deaktivert"));
            counter = COUNTER_HODEMELDING_LABEL;
        } else {
            log.info("ukjent melding til Padm2: " + (leggMeldingerPaKo ? "Sender" : "Sending deaktivert"));
            counter = COUNTER_ANNEN_MELDING_LABEL;
        }

        if (leggMeldingerPaKo) {
            of(fellesformat)
                    .map(Fellesformat::getMessage)
                    .ifPresent(jmsSender);
        }
        counter.increment();
    }

    @Inject
    public void setJmsMottakQueuePadm2Meldinger(@Qualifier("Padm2JmsTemplate") JmsTemplate jmsMottakQueuePadm2Meldinger) {
        this.padm2JmsTemplate = jmsMottakQueuePadm2Meldinger;
    }
}
