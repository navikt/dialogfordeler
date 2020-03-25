package no.nav.syfo.consumer.mq;

import com.atomikos.datasource.pool.PoolExhaustedException;
import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.exception.MeldingInboundException;
import no.nav.syfo.service.MeldingRuter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import static java.util.Optional.ofNullable;
import static no.nav.syfo.util.MDCOperations.*;
import static io.micrometer.core.instrument.Metrics.counter;

@Component
@Slf4j
public class DialogmeldingerConsumer {
    private MeldingRuter meldingRuter;

    private static final Counter COUNTER_JMS_ERROR_GENERAL = counter("dialogfordeler_jms_error_general", "type", "jms");
    private static final Counter COUNTER_JMS_ERROR_POOL = counter("dialogfordeler_jms_error_pool", "type", "jms");

    @Transactional
    @JmsListener(id = "dialogmeldinger_listener", containerFactory = "jmsListenerContainerFactory", destination = "dialogmeldingerQueue")
    public void listen(Object message) {
        putToMDC(MDC_CALL_ID, generateCallId());
        TextMessage textMessage = (TextMessage) message;
        try {
            ofNullable(textMessage.getStringProperty("callId")).ifPresent(callId -> putToMDC(MDC_CALL_ID, callId));
            meldingRuter.evaluer(textMessage);
        } catch (JMSException e) {
            log.error("Feil ved lesing av melding", e);
            throw new MeldingInboundException("Feil ved lesing av melding", e);
            COUNTER_JMS_ERROR_GENERAL.increment();
        } catch (PoolExhaustedException e) {
            log.error("Fikk PoolExhaustedException:", e);
            COUNTER_JMS_ERROR_POOL.increment();
        } finally {
            remove(MDC_CALL_ID);
        }
    }

    @Inject
    public void setMeldingRuter(MeldingRuter meldingRuter) {
        this.meldingRuter = meldingRuter;
    }
}
