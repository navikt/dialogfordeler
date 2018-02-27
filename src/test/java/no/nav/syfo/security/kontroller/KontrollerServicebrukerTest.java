package no.nav.syfo.security.kontroller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.User;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class KontrollerServicebrukerTest {
    private KontrollerServicebruker kontrollerServicebruker;

    @Before
    public void setUp() throws Exception {
        kontrollerServicebruker = new KontrollerServicebruker();
    }

    @Test
    public void erServicebruker() {
        User user = new User("srvUsername", "Password", emptyList());
        assertThat(kontrollerServicebruker.erServicebruker(user)).isTrue();
    }

    @Test
    public void erIkkeServicebruker() {
        User user = new User("username", "Password", emptyList());
        assertThat(kontrollerServicebruker.erServicebruker(user)).isFalse();
    }
}