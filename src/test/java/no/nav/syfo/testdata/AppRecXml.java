package no.nav.syfo.testdata;

public class AppRecXml {
    public static final int APPREC1_0 = 0b1;
    public static final int APPREC1_1 = 0b10;

    public static String apprec(int innhold) {
        return (innhold & APPREC1_0) == APPREC1_0 ? apprec1_0() : apprec1_1();
    }

    private static String apprec1_0() {
        return "<AppRec xmlns=\"http://www.kith.no/xmlstds/apprec/2004-11-21\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema.xsd\"\n" +
                "        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "        xsi:schemaLocation=\"http://www.kith.no/xmlstds/apprec/2004-11-21 AppRec-v1-2004-11-21.xsd\">\n" +
                "    <MsgType V=\"APPREC\"/>\n" +
                "    <MIGversion>1.0 2004-11-21</MIGversion>\n" +
                "    <GenDate>2008-12-17T10:31:07</GenDate>\n" +
                "    <Id>1d6b0e00-33a1-11de-9255-0002a5d5c51b</Id>\n" +
                "    <Sender>\n" +
                "        <Role DN=\"Kopimottaker\" V=\"COP\"/>\n" +
                "        <HCP>\n" +
                "            <Inst>\n" +
                "                <Name>Vassenden legekontor</Name>\n" +
                "                <Id>974793539</Id>\n" +
                "                <TypeId DN=\"Organisasjonsnummeret i Enhetsregister\" V=\"ENH\"/>\n" +
                "                <HCPerson>\n" +
                "                    <Name>Rita Lin</Name>\n" +
                "                    <Id>9144900</Id>\n" +
                "                    <TypeId DN=\"Helsepersonellnummer\" V=\"HPR\"/>\n" +
                "                </HCPerson>\n" +
                "            </Inst>\n" +
                "        </HCP>\n" +
                "    </Sender>\n" +
                "    <Receiver>\n" +
                "        <Role V=\"RECEIVER\"/>\n" +
                "        <HCP>\n" +
                "            <Inst>\n" +
                "                <Name>Køfri sykehus HF</Name>\n" +
                "                <Id>974744570</Id>\n" +
                "                <TypeId DN=\"Organisasjonsnummeret i Enhetsregister\" V=\"ENH\"/>\n" +
                "            </Inst>\n" +
                "        </HCP>\n" +
                "    </Receiver>\n" +
                "    <Status DN=\"OK\" V=\"1\"/>\n" +
                "    <OriginalMsgId>\n" +
                "        <MsgType DN=\"Epikrise\" V=\"EPIKRISE\"/>\n" +
                "        <IssueDate>2008-12-17T08:32:15</IssueDate>\n" +
                "        <Id>a6967e6a-8c0a-4be4-a647-c921d3086423</Id>\n" +
                "    </OriginalMsgId>\n" +
                "</AppRec>";
    }

    private static String apprec1_1() {
        return "<AppRec xmlns=\"http://www.kith.no/xmlstds/apprec/2012-02-15\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema.xsd\"\n" +
                "        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "        xsi:schemaLocation=\"http://www.kith.no/xmlstds/apprec/2012-02-15 AppRec-v1-2004-11-21.xsd\">\n" +
                "    <MsgType V=\"APPREC\"/>\n" +
                "    <MIGversion>1.0 2004-11-21</MIGversion>\n" +
                "    <GenDate>2008-12-17T10:31:07</GenDate>\n" +
                "    <Id>1d6b0e00-33a1-11de-9255-0002a5d5c51b</Id>\n" +
                "    <Sender>\n" +
                "        <Role DN=\"Kopimottaker\" V=\"COP\"/>\n" +
                "        <HCP>\n" +
                "            <Inst>\n" +
                "                <Name>Vassenden legekontor</Name>\n" +
                "                <Id>974793539</Id>\n" +
                "                <TypeId DN=\"Organisasjonsnummeret i Enhetsregister\" V=\"ENH\"/>\n" +
                "                <HCPerson>\n" +
                "                    <Name>Rita Lin</Name>\n" +
                "                    <Id>9144900</Id>\n" +
                "                    <TypeId DN=\"Helsepersonellnummer\" V=\"HPR\"/>\n" +
                "                </HCPerson>\n" +
                "            </Inst>\n" +
                "        </HCP>\n" +
                "    </Sender>\n" +
                "    <Receiver>\n" +
                "        <Role V=\"RECEIVER\"/>\n" +
                "        <HCP>\n" +
                "            <Inst>\n" +
                "                <Name>Køfri sykehus HF</Name>\n" +
                "                <Id>974744570</Id>\n" +
                "                <TypeId DN=\"Organisasjonsnummeret i Enhetsregister\" V=\"ENH\"/>\n" +
                "            </Inst>\n" +
                "        </HCP>\n" +
                "    </Receiver>\n" +
                "    <Status DN=\"OK\" V=\"1\"/>\n" +
                "    <OriginalMsgId>\n" +
                "        <MsgType DN=\"Epikrise\" V=\"EPIKRISE\"/>\n" +
                "        <IssueDate>2008-12-17T08:32:15</IssueDate>\n" +
                "        <Id>a6967e6a-8c0a-4be4-a647-c921d3086423</Id>\n" +
                "    </OriginalMsgId>\n" +
                "</AppRec>";
    }
}