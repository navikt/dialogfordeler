package no.nav.syfo.testdata;

public class FellesformatXml {

    public static String fellesformat(String s) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ns3:EI_fellesformat xmlns=\"http://www.kith.no/xmlstds/msghead/2006-05-24\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" " +
                "xmlns:ns3=\"http://www.trygdeetaten.no/xml/eiff/1/\" xmlns:ns4=\"http://www.kith.no/xmlstds/apprec/2004-11-21\" " +
                "xmlns:ns5=\"http://www.kith.no/xmlstds/dialog/2006-10-11\" xmlns:ns6=\"http://www.kith.no/xmlstds/felleskomponent1\" " +
                "xmlns:ns7=\"http://www.kith.no/xmlstds/base64container\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xsi:schemaLocation=\"http://www.trygdeetaten.no/xml/eiff/1/ Fellesformat_1.0.xsd\">\n" +
                (s != null ? s : "") +
                "</ns3:EI_fellesformat>";
    }
}