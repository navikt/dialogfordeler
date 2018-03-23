package no.nav.syfo.service;

import no.nav.syfo.domain.hodemeldingwrapper.Hodemelding;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class SyfoMeldingServiceTest {
    private SyfoMeldingService syfoMeldingService;

    @Before
    public void setUp() {
        syfoMeldingService = new SyfoMeldingService();
    }

    @Test(expected = IllegalStateException.class)
    public void doSomething() throws Exception {
        syfoMeldingService.doSomething(mock(Hodemelding.class));
    }
}