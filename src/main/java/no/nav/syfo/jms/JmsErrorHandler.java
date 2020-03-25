package no.nav.syfo.jms;

import com.atomikos.datasource.pool.PoolExhaustedException;
import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import static io.micrometer.core.instrument.Metrics.counter;

@Slf4j
@Component
public class JmsErrorHandler implements ErrorHandler {

    private static final Counter COUNTER_JMS_ERROR_GENERAL = counter("dialogfordeler_jms_error_general", "type", "jms");
    private static final Counter COUNTER_JMS_ERROR_POOL = counter("dialogfordeler_jms_error_pool", "type", "jms");

    @Override
    public void handleError(Throwable t) {
        if (t instanceof PoolExhaustedException) {
            log.error("JmsListener fikk PoolExhaustedException: ", t);
            COUNTER_JMS_ERROR_POOL.increment();
            throw new RuntimeException("JmsListener PoolExhaustedException feil: ", t);
        }
        log.error("Feil under håndtering av inkommende JMS melding: ", t);
        COUNTER_JMS_ERROR_GENERAL.increment();
        throw new RuntimeException("Generell JmsListener feil: ", t);
    }
}