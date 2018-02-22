package no.nav.syfo.provider.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/api")
public class ApiController {
    @RequestMapping()
    @PreAuthorize("erServicebruker()")
    public Some api() {
        return new Some("Noe", "Annet");
    }

    class Some {
        public String noe;
        public String annet;
        public LocalDateTime dateTime;

        Some(String noe, String annet) {
            this.noe = noe;
            this.annet = annet;
            this.dateTime = now();
        }
    }
}
