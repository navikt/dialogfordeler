package no.nav.syfo.util;

import no.nav.modig.common.MDCOperations;
import org.springframework.jms.core.MessageCreator;

import javax.jms.TextMessage;

import static java.util.Optional.ofNullable;
import static no.nav.modig.common.MDCOperations.MDC_CALL_ID;
import static no.nav.modig.common.MDCOperations.getFromMDC;

public final class JmsUtil {
    public static MessageCreator messageCreator(final String message) {
        String callId = ofNullable(getFromMDC(MDC_CALL_ID))
                .orElseGet(MDCOperations::generateCallId);
        return messageCreator(message, callId);
    }

    public static MessageCreator messageCreator(final String message, final String callId) {
        return session -> {
            TextMessage textMessage = session.createTextMessage(message);
            textMessage.setStringProperty("callId", callId);
            return textMessage;
        };
    }
}
