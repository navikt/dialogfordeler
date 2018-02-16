package no.nav.syfo.props;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ConfigurationProperties("EIA_QUEUE_MOTTAK_MELDING_OUTBOUND")
@Component
@Validated
public class MqEiaQueueMottakMeldingOutboundProperties {
    @NotEmpty
    private String queuename;
}
