package no.nav.syfo.service;

import no.nav.syfo.domain.apprecwrapper.AppRec;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class AppRecServiceTest {
    private AppRecService appRecService;

    @Before
    public void setUp() {
        appRecService = new AppRecService();
    }

    @Test(expected = IllegalStateException.class)
    public void doSomething() throws Exception {
        appRecService.doSomething(mock(AppRec.class));
    }
}