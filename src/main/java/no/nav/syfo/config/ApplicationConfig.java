package no.nav.syfo.config;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.web.EnablePrometheusTiming;
import no.nav.syfo.props.TestProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnablePrometheusEndpoint
@EnablePrometheusTiming
@EnableConfigurationProperties({TestProperties.class})
@Configuration
public class ApplicationConfig {
}
