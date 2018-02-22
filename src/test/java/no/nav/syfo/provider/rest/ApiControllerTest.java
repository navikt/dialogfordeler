package no.nav.syfo.provider.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ApiControllerTest {
    @InjectMocks
    private ApiController apiController;

    @Test
    public void api() {
        ApiController.Some some = apiController.api();
        assertThat(some.noe).isEqualTo("Noe");
    }
}