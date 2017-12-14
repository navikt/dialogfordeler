package no.nav.syfo.props;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Getter
@Setter
@ConfigurationProperties("MQGATEWAY03")
@Component
@Validated
public class MqGatewayProperties {
    @NotEmpty
    private String hostname;
    @NotEmpty
    private String name;
    @Min(0)
    private int port;
}
