package no.nav.syfo.util;

import no.kith.xmlstds.msghead._2006_05_24.XMLMsgHead;
import no.kith.xmlstds.msghead._2006_05_24.XMLMsgInfo;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JAXBTest {

    @Test
    public void marshallHodemelding() {
        String hodemelding = JAXB.marshallHodemelding(new XMLMsgHead().withMsgInfo(new XMLMsgInfo().withMsgId("msgId")));
        assertThat(hodemelding)
                .isEqualTo("<MsgHead xmlns=\"http://www.kith.no/xmlstds/msghead/2006-05-24\" " +
                        "xmlns:ns6=\"http://www.kith.no/xmlstds/base64container\" " +
                        "xmlns:ns5=\"http://www.kith.no/xmlstds/dialog/2013-01-23\" " +
                        "xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" " +
                        "xmlns:ns4=\"http://www.kith.no/xmlstds/felleskomponent1\" " +
                        "xmlns:ns3=\"http://www.kith.no/xmlstds/dialog/2006-10-11\">\n" +
                        "    <MsgInfo>\n" +
                        "        <MsgId>msgId</MsgId>\n" +
                        "    </MsgInfo>\n" +
                        "</MsgHead>");
    }

    @Test
    public void unmarshalHodemelding() {
        final XMLMsgHead hodemelding = JAXB.unmarshalHodemelding("<MsgHead xmlns=\"http://www.kith.no/xmlstds/msghead/2006-05-24\" " +
                "xmlns:ns6=\"http://www.kith.no/xmlstds/base64container\" " +
                "xmlns:ns5=\"http://www.kith.no/xmlstds/dialog/2013-01-23\" " +
                "xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" " +
                "xmlns:ns4=\"http://www.kith.no/xmlstds/felleskomponent1\" " +
                "xmlns:ns3=\"http://www.kith.no/xmlstds/dialog/2006-10-11\">\n" +
                "    <MsgInfo>\n" +
                "        <MsgId>msgId</MsgId>\n" +
                "    </MsgInfo>\n" +
                "</MsgHead>");
        assertThat(hodemelding.getMsgInfo().getMsgId()).isEqualTo("msgId");
    }
}