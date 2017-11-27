package no.nav.syfo;

import no.nav.syfo.util.FasitPropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        FasitPropertiesUtil.setAppConfig();
        SpringApplication.run(Application.class, args);
    }
}
