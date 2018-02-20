package no.nav.syfo.config;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.web.EnablePrometheusTiming;
import org.springframework.context.annotation.Configuration;

@EnablePrometheusEndpoint
@EnablePrometheusTiming
@Configuration
public class ApplicationConfig {
}


