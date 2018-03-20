package no.nav.syfo.web.rest.dialogmelding;

import no.nav.syfo.web.rest.dialogmelding.model.RSDialogmelding;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DialogmeldingControllerTest {
    @Mock
    private DialogmeldingService dialogmeldingService;
    @InjectMocks
    private DialogmeldingController dialogmeldingController;

    @Test
    public void opprettDialogmelding() {
        RSDialogmelding dialogmelding = new RSDialogmelding();

        dialogmeldingController.opprettDialogmelding(dialogmelding);

        verify(dialogmeldingService).registrerDialogmelding(same(dialogmelding));
    }
}