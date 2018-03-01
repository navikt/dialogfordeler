package no.nav.syfo.util;

import no.kith.xmlstds.msghead._2006_05_24.XMLMsgHead;
import no.kith.xmlstds.msghead._2006_05_24.XMLMsgInfo;
import no.nav.xml.eiff._2.XMLEIFellesformat;
import no.nav.xml.eiff._2.XMLMottakenhetBlokk;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JAXBTest {

    @Test
    public void marshallHodemelding() {
        String hodemelding = JAXB.marshallMelding(new XMLEIFellesformat()
                .withAny(new XMLMsgHead()
                        .withMsgInfo(new XMLMsgInfo()
                                .withMsgId("msgId")))
                .withAny(new XMLMottakenhetBlokk()
                        .withEdiLoggId("ediLoggId")));
        assertThat(hodemelding)
                .isEqualTo("<EI_fellesformat " +
                        "xmlns=\"http://www.trygdeetaten.no/xml/eiff/1/\" " +
                        "xmlns:ns6=\"http://www.kith.no/xmlstds/dialog/2013-01-23\" " +
                        "xmlns:ns5=\"http://www.kith.no/xmlstds/felleskomponent1\" " +
                        "xmlns:ns8=\"http://www.kith.no/xmlstds/apprec/2004-11-21\" " +
                        "xmlns:ns7=\"http://www.kith.no/xmlstds/base64container\" " +
                        "xmlns:ns9=\"http://www.kith.no/xmlstds/apprec/2012-02-15\" " +
                        "xmlns:ns2=\"http://www.kith.no/xmlstds/msghead/2006-05-24\" " +
                        "xmlns:ns4=\"http://www.kith.no/xmlstds/dialog/2006-10-11\" " +
                        "xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                        "    <ns2:MsgHead>\n" +
                        "        <ns2:MsgInfo>\n" +
                        "            <ns2:MsgId>msgId</ns2:MsgId>\n" +
                        "        </ns2:MsgInfo>\n" +
                        "    </ns2:MsgHead>\n" +
                        "    <MottakenhetBlokk ediLoggId=\"ediLoggId\"/>\n" +
                        "</EI_fellesformat>");
    }

    @Test
    public void unmarshalHodemelding() {
        final XMLEIFellesformat fellesformat = JAXB.unmarshalMelding("<EI_fellesformat " +
                "xmlns=\"http://www.trygdeetaten.no/xml/eiff/1/\" " +
                "xmlns:ns6=\"http://www.kith.no/xmlstds/dialog/2013-01-23\" " +
                "xmlns:ns5=\"http://www.kith.no/xmlstds/felleskomponent1\" " +
                "xmlns:ns8=\"http://www.kith.no/xmlstds/apprec/2004-11-21\" " +
                "xmlns:ns7=\"http://www.kith.no/xmlstds/base64container\" " +
                "xmlns:ns9=\"http://www.kith.no/xmlstds/apprec/2012-02-15\" " +
                "xmlns:ns2=\"http://www.kith.no/xmlstds/msghead/2006-05-24\" " +
                "xmlns:ns4=\"http://www.kith.no/xmlstds/dialog/2006-10-11\" " +
                "xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "    <ns2:MsgHead>\n" +
                "        <ns2:MsgInfo>\n" +
                "            <ns2:MsgId>msgId</ns2:MsgId>\n" +
                "        </ns2:MsgInfo>\n" +
                "    </ns2:MsgHead>\n" +
                "    <MottakenhetBlokk ediLoggId=\"ediLoggId\"/>\n" +
                "</EI_fellesformat>");
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(((XMLMsgHead) fellesformat.getAny().get(0)).getMsgInfo().getMsgId()).isEqualTo("msgId");
        softAssertions.assertThat(((XMLMottakenhetBlokk) fellesformat.getAny().get(1)).getEdiLoggId()).isEqualTo("ediLoggId");
        softAssertions.assertAll();
    }
}