package no.nav.syfo.util;

import no.nav.modig.common.MDCOperations;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Message;

import static java.util.Optional.ofNullable;
import static no.nav.modig.common.MDCOperations.MDC_CALL_ID;
import static no.nav.modig.common.MDCOperations.getFromMDC;

public final class JmsUtil {
    public static MessageCreator messageCreator(final Message message) {
        String callId = ofNullable(getFromMDC(MDC_CALL_ID))
                .orElseGet(MDCOperations::generateCallId);
        return messageCreator(message, callId);
    }

    public static MessageCreator messageCreator(final Message message, final String callId) {
        return session -> {
            message.setStringProperty("callId", callId);
            return message;
        };
    }
}
