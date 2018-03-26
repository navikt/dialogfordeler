package no.nav.syfo.web.rest.dialogmelding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RSMottaker {
    private String herId;
    private String partnerId;
    private String orgnummer;
    private String navn;
    private String adresse;
    private String postnummer;
    private String poststed;
    private RSBehandler behandler;
}


