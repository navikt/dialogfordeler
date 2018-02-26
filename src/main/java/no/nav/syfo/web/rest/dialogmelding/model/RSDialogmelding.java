package no.nav.syfo.web.rest.dialogmelding.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RSDialogmelding {
    private String meldingId;
    private String lege;
    private String pasient;

    public RSDialogmelding() {
    }

    public RSDialogmelding(String meldingId, String lege, String pasient) {
        this.meldingId = meldingId;
        this.lege = lege;
        this.pasient = pasient;
    }
}