package no.nav.syfo.provider.mq;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Queue;

@Component
public class EiaQueueMottakOutboundProvider {
    private Queue eiaQueueMottakOutbound;

    @Inject
    public void setEiaQueueMottakOutbound(Queue eiaQueueMottakOutbound) {
        this.eiaQueueMottakOutbound = eiaQueueMottakOutbound;
    }
}
