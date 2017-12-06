package no.nav.syfo.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@ConfigurationProperties("DIALOGFORDELER_CHANNEL")
@Component
@Validated
public class MqChannelProperties {
    @NotEmpty
    private String name;
}
