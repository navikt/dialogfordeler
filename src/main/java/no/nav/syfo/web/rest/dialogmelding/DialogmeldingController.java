package no.nav.syfo.web.rest.dialogmelding;

import no.nav.syfo.web.rest.dialogmelding.model.RSDialogmelding;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/dialogmelding")
public class DialogmeldingController {
    private DialogmeldingService dialogmeldingService;

    public DialogmeldingController(DialogmeldingService dialogmeldingService) {
        this.dialogmeldingService = dialogmeldingService;
    }

    @RequestMapping(value = "/opprett", method = POST, consumes = APPLICATION_JSON)
    @PreAuthorize("@kontrollerServicebruker.erServicebruker(principal)")
    public void opprettDialogmelding(@RequestBody RSDialogmelding dialogmelding) {
        dialogmeldingService.registrerDialogmelding(dialogmelding);
    }
}