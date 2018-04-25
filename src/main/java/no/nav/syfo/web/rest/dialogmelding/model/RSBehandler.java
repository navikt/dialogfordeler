package no.nav.syfo.web.rest.dialogmelding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RSBehandler {
    private String fnr;
    private String hprId;
    private String fornavn;
    private String mellomnavn;
    private String etternavn;
}