package no.nav.syfo.domain.apprecwrapper;

import lombok.Data;
import lombok.NonNull;
import no.kith.xmlstds.apprec._2012_02_15.XMLAppRec;

@Data
public class AppRec1_1 implements AppRec {
    @NonNull
    private XMLAppRec appRec;

    public String originalMessageId(){
        return appRec.getOriginalMsgId().getId();
    }
}
