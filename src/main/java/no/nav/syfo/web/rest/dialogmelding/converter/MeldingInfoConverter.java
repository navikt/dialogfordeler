package no.nav.syfo.web.rest.dialogmelding.converter;

import no.kith.xmlstds.msghead._2006_05_24.ObjectFactory;
import no.kith.xmlstds.msghead._2006_05_24.XMLMsgInfo;
import no.nav.syfo.web.rest.dialogmelding.model.RSMeldingInfo;

import java.time.LocalDateTime;
import java.util.UUID;

public class MeldingInfoConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private SenderConverter senderConverter;
    private MottakerConverter mottakerConverter;
    private PasientConverter pasientConverter;

    private XMLMsgInfo msgInfo;

    public MeldingInfoConverter(RSMeldingInfo rsMeldingInfo) {
        this.senderConverter = new SenderConverter(rsMeldingInfo.getSender());
        this.mottakerConverter = new MottakerConverter(rsMeldingInfo.getMottaker());
        this.pasientConverter = new PasientConverter(rsMeldingInfo.getPasient());
    }

    public XMLMsgInfo getMsgInfo() {
        ensureMsgInfo();
        return this.msgInfo;
    }

    private void ensureMsgInfo() {
        if (this.msgInfo == null) {
            this.msgInfo = FACTORY.createXMLMsgInfo()
                    .withType(FACTORY.createXMLCS()
                            .withDN("Forespørsel")
                            .withV("DIALOG_FORESPORSEL"))
                    .withMIGversion("v1.2 2006-05-24")
                    .withGenDate(LocalDateTime.now())
                    .withMsgId(UUID.randomUUID().toString())
                    .withAck(FACTORY.createXMLCS()
                            .withDN("Ja")
                            .withV("J"))
                    .withSender(senderConverter.getSender())
                    .withReceiver(mottakerConverter.getReceiver())
                    .withPatient(pasientConverter.getPatient());
        }
    }
}
