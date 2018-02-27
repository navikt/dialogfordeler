package no.nav.syfo.security.kontroller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static java.util.stream.Stream.of;

@Component
public class KontrollerServicebruker {
    public boolean erServicebruker(UserDetails userDetails) {
        Pattern pattern = compile("srv[a-zA-ZæøåÆØÅ0-9]+");

        return of(userDetails)
                .map(UserDetails::getUsername)
                .map(pattern::matcher)
                .anyMatch(Matcher::matches);
    }
}
