package no.nav.syfo.config;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.web.EnablePrometheusTiming;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@EnablePrometheusEndpoint
@EnablePrometheusTiming
@Configuration
@EnableTransactionManagement
public class ApplicationConfig {
    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) throws ParseException {
                return LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
            }

            @Override
            public String print(LocalDateTime localDateTime, Locale locale) {
                return DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
            }
        };
    }
}


