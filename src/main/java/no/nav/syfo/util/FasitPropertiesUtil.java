package no.nav.syfo.util;

import lombok.extern.slf4j.Slf4j;

import java.io.StringReader;
import java.util.Properties;

@Slf4j
public final class FasitPropertiesUtil {
    private static final String PROPSOURCE = "dialogfordelerProperties_applicationProperties";

    public static void setAppConfig() {
        String applicationPropertiesFromSystemProperties = System.getProperty(PROPSOURCE);
        if (applicationPropertiesFromSystemProperties == null) {
            log.info("SystemProperties er blank");
        }
        setSystemProperties(applicationPropertiesFromSystemProperties);
        String applicationPropertiesFromEnv = System.getenv(PROPSOURCE);
        if (applicationPropertiesFromEnv == null) {
            log.info("EnvProperties er blank");
        }
        setSystemProperties(applicationPropertiesFromEnv);
    }

    private static void setSystemProperties(String applicationProperties) {
        if (applicationProperties != null) {
            Properties properties = new Properties();
            try {
                properties.load(new StringReader(applicationProperties));
                for (Object propKey : properties.keySet()) {
                    String oldPropValue = System.getProperty((String) propKey);
                    log.info("Setting System property={}, value={} from env source " + PROPSOURCE +
                            (oldPropValue != null ? ". Overriding existing value=" + oldPropValue : ""), propKey, properties.get(propKey));
                    System.setProperty((String) propKey, properties.getProperty((String) propKey));
                }
            } catch (Exception e) {
                log.error("Unable to read " + PROPSOURCE + " env variable into System.properties", e);
                // continue, the properties could have been supplied from other sources
            }
        }
    }
}
