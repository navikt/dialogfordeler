package no.nav.syfo.service;

import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class HodemeldingServiceTest {
    private HodemeldingService hodemeldingService;

    @Before
    public void setUp() {
        hodemeldingService = new HodemeldingService();
    }

    @Test(expected = IllegalStateException.class)
    public void doSomething() throws Exception {
        hodemeldingService.doSomething(mock(Hodemelding.class));
    }
}