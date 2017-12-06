package no.nav.syfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.Properties;

@SpringBootApplication
@Slf4j
public class Application {
    public static void main(String[] args) {
        setSystemProperties();
        SpringApplication.run(Application.class, args);
    }

    private static void setSystemProperties() {
        final Properties systemProperties = System.getProperties();
        final Map<String, String> envProperties = System.getenv();
        envProperties.forEach(systemProperties::putIfAbsent);
        envProperties.forEach((key, value) -> systemProperties.putIfAbsent(convertKey(key), value));

        systemProperties.forEach((k, v) -> log.info("Property: {}", k));
    }

    private static String convertKey(String key) {
        return key.toLowerCase().replaceAll("_", ".");
    }
}
