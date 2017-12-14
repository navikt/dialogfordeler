package no.nav.syfo.testdata;

public class HodemeldingXml {

    public static final int NOTAT0 = 0b1;
    public static final int NOTAT1 = 0b10;
    public static final int DIALOG0 = 0b100;
    public static final int DIALOG1 = 0b1000;
    public static final int VEDLEGG = 0b10000;

    public static String hodemelding(int innhold) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<mh:MsgHead xmlns:b64c=\"http://www.kith.no/xmlstds/base64container\"\n" +
                "            xmlns:dia=\"http://www.kith.no/xmlstds/dialog/2013-01-23\"\n" +
                "            xmlns:dia2=\"http://www.kith.no/xmlstds/dialog/2006-10-11\"\n" +
                "            xmlns:mh=\"http://www.kith.no/xmlstds/msghead/2006-05-24\"\n" +
                "            xsi:schemaLocation=\"http://www.kith.no/xmlstds/msghead/2006-05-24 MsgHead-v1_2.xsd\"\n" +
                "            xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <mh:MsgInfo>\n" +
                "        <mh:Type DN=\"Forespørsel\" V=\"DIALOG_FORESPORSEL\"/>\n" +
                "        <mh:MIGversion>v1.2 2006-05-24</mh:MIGversion>\n" +
                "        <mh:GenDate>2017-09-29T14:00:24.876+02:00</mh:GenDate>\n" +
                "        <mh:MsgId>cdbf76f2-0479-4c68-b7b9-384a0e9a3fe1</mh:MsgId>\n" +
                "        <mh:Ack DN=\"Ja\" V=\"J\"/>\n" +
                "        <mh:Sender>\n" +
                "            <mh:Organisation>\n" +
                "                <mh:OrganisationName>NAV</mh:OrganisationName>\n" +
                "                <mh:Ident>\n" +
                "                    <mh:Id>123456789</mh:Id>\n" +
                "                    <mh:TypeId DN=\"Organisasjonsnummeret i Enhetsregisteret\" S=\"2.16.578.1.12.4.1.1.9051\" V=\"ENH\"/>\n" +
                "                </mh:Ident>\n" +
                "                <mh:Ident>\n" +
                "                    <mh:Id>12345</mh:Id>\n" +
                "                    <mh:TypeId DN=\"Identifikator fra Helsetjenesteenhetsregisteret (HER-id)\"\n" +
                "                               S=\"2.16.578.1.12.4.1.1.9051\" V=\"HER\"/>\n" +
                "                </mh:Ident>\n" +
                "                <mh:Organisation>\n" +
                "                    <mh:OrganisationName>NAV B{rum</mh:OrganisationName>\n" +
                "                    <mh:Ident>\n" +
                "                        <mh:Id>0000</mh:Id>\n" +
                "                        <mh:TypeId DN=\"Lokal identifikator for institusjoner\" S=\"2.16.578.1.12.4.1.1.9051\" V=\"LIN\"/>\n" +
                "                    </mh:Ident>\n" +
                "                </mh:Organisation>\n" +
                "            </mh:Organisation>\n" +
                "        </mh:Sender>\n" +
                "        <mh:Receiver>\n" +
                "            <mh:Organisation>\n" +
                "                <mh:OrganisationName>Org AS</mh:OrganisationName>\n" +
                "                <mh:Ident>\n" +
                "                    <mh:Id>54321</mh:Id>\n" +
                "                    <mh:TypeId DN=\"HER-Id\" S=\"2.16.578.1.12.4.1.1.9051\" V=\"HER\"/>\n" +
                "                </mh:Ident>\n" +
                "                <mh:Ident>\n" +
                "                    <mh:Id>234567890</mh:Id>\n" +
                "                    <mh:TypeId DN=\"Organisasjonsnummer i Enhetsregister\" S=\"2.16.578.1.12.4.1.1.9051\" V=\"ENH\"/>\n" +
                "                </mh:Ident>\n" +
                "                <mh:Address>\n" +
                "                    <mh:StreetAdr>Andreveien 14</mh:StreetAdr>\n" +
                "                    <mh:PostalCode>0140</mh:PostalCode>\n" +
                "                    <mh:City>Bortenfor</mh:City>\n" +
                "                </mh:Address>\n" +
                "                <mh:HealthcareProfessional>\n" +
                "                    <mh:RoleToPatient V=\"4\"/>\n" +
                "                    <mh:FamilyName>Hansen</mh:FamilyName>\n" +
                "                    <mh:GivenName>Hans</mh:GivenName>\n" +
                "                    <mh:Ident>\n" +
                "                        <mh:Id>16124704139</mh:Id>\n" +
                "                        <mh:TypeId DN=\"Fødselsnummer Norsk fødselsnummer\" S=\"2.16.578.1.12.4.1.1.8116\" V=\"FNR\"/>\n" +
                "                    </mh:Ident>\n" +
                "                    <mh:Ident>\n" +
                "                        <mh:Id>582357375</mh:Id>\n" +
                "                        <mh:TypeId DN=\"HPR-nummer\" S=\"2.16.578.1.12.4.1.1.8116\" V=\"HPR\"/>\n" +
                "                    </mh:Ident>\n" +
                "                </mh:HealthcareProfessional>\n" +
                "            </mh:Organisation>\n" +
                "        </mh:Receiver>\n" +
                "        <mh:Patient>\n" +
                "            <mh:FamilyName>Olsen</mh:FamilyName>\n" +
                "            <mh:GivenName>Oline</mh:GivenName>\n" +
                "            <mh:Ident>\n" +
                "                <mh:Id>12059413423</mh:Id>\n" +
                "                <mh:TypeId DN=\"Fødselsnummer\" S=\"2.16.578.1.12.4.1.1.8116\" V=\"FNR\"/>\n" +
                "            </mh:Ident>\n" +
                "        </mh:Patient>\n" +
                "    </mh:MsgInfo>\n" +
                ((innhold & NOTAT0) == NOTAT0 ? notat1_0() : "") +
                ((innhold & NOTAT1) == NOTAT1 ? notat1_1() : "") +
                ((innhold & DIALOG0) == DIALOG0 ? dialogmelding1_0() : "") +
                ((innhold & DIALOG1) == DIALOG1 ? dialogmelding1_1() : "") +
                ((innhold & VEDLEGG) == VEDLEGG ? vedlegg() : "") +
                "</mh:MsgHead>\n";
    }

    private static String dialogmelding1_0() {
        return "    <mh:Document>\n" +
                "        <mh:DocumentConnection DN=\"Hoveddokument\" V=\"H\"/>\n" +
                "        <mh:RefDoc>\n" +
                "            <mh:IssueDate V=\"2017-09-29\"/>\n" +
                "            <mh:MsgType DN=\"XML-instans\" V=\"XML\"/>\n" +
                "            <mh:MimeType>text/xml</mh:MimeType>\n" +
                "            <mh:Content>\n" +
                "                <dia2:Dialogmelding>\n" +
                "                    <dia2:Foresporsel>\n" +
                "                        <dia2:TypeForesp DN=\"Endring dialogmøte 3\" S=\"2.16.578.1.12.4.1.1.8125\" V=\"4\"/>\n" +
                "                        <dia2:Sporsmal>Sporsmal</dia2:Sporsmal>\n" +
                "                        <dia2:DokIdForesp>OD1709294461894</dia2:DokIdForesp>\n" +
                "                        <dia2:RollerRelatertNotat>\n" +
                "                            <dia2:RolleNotat S=\"2.16.578.1.12.4.1.1.9057\" V=\"1\"/>\n" +
                "                            <dia2:Person>\n" +
                "                                <dia2:GivenName>Hege</dia2:GivenName>\n" +
                "                                <dia2:FamilyName>Johnsen</dia2:FamilyName>\n" +
                "                            </dia2:Person>\n" +
                "                        </dia2:RollerRelatertNotat>\n" +
                "                    </dia2:Foresporsel>\n" +
                "                </dia2:Dialogmelding>\n" +
                "            </mh:Content>\n" +
                "        </mh:RefDoc>\n" +
                "    </mh:Document>\n";
    }

    private static String dialogmelding1_1() {
        return "    <mh:Document>\n" +
                "        <mh:DocumentConnection DN=\"Hoveddokument\" V=\"H\"/>\n" +
                "        <mh:RefDoc>\n" +
                "            <mh:IssueDate V=\"2017-09-29\"/>\n" +
                "            <mh:MsgType DN=\"XML-instans\" V=\"XML\"/>\n" +
                "            <mh:MimeType>text/xml</mh:MimeType>\n" +
                "            <mh:Content>\n" +
                "                <dia:Dialogmelding>\n" +
                "                    <dia:Foresporsel>\n" +
                "                        <dia:TypeForesp DN=\"Endring dialogmøte 3\" S=\"2.16.578.1.12.4.1.1.8125\" V=\"4\"/>\n" +
                "                        <dia:EmneSporsmal>Emne</dia:EmneSporsmal>\n" +
                "                        <dia:Sporsmal>Sporsmal</dia:Sporsmal>\n" +
                "                        <dia:DokIdForesp>OD1709294461894</dia:DokIdForesp>\n" +
                "                        <dia:RollerRelatertNotat>\n" +
                "                            <dia:RolleNotat S=\"2.16.578.1.12.4.1.1.9057\" V=\"1\"/>\n" +
                "                            <dia:Person>\n" +
                "                                <dia:GivenName>Hege</dia:GivenName>\n" +
                "                                <dia:FamilyName>Johnsen</dia:FamilyName>\n" +
                "                            </dia:Person>\n" +
                "                        </dia:RollerRelatertNotat>\n" +
                "                    </dia:Foresporsel>\n" +
                "                </dia:Dialogmelding>\n" +
                "            </mh:Content>\n" +
                "        </mh:RefDoc>\n" +
                "    </mh:Document>\n";
    }

    private static String notat1_0() {
        return "    <mh:Document>\n" +
                "        <mh:DocumentConnection DN=\"Hoveddokument\" V=\"H\"/>\n" +
                "        <mh:RefDoc>\n" +
                "            <mh:IssueDate V=\"2017-09-29\"/>\n" +
                "            <mh:MsgType DN=\"XML-instans\" V=\"XML\"/>\n" +
                "            <mh:MimeType>text/xml</mh:MimeType>\n" +
                "            <mh:Content>\n" +
                "                <dia2:Dialogmelding>\n" +
                "                    <dia2:Notat>\n" +
                "                        <dia2:TemaKodet/>\n" +
                "                        <dia2:DokIdNotat>dokidnotat1_0</dia2:DokIdNotat>\n" +
                "                    </dia2:Notat>\n" +
                "                </dia2:Dialogmelding>\n" +
                "            </mh:Content>\n" +
                "        </mh:RefDoc>\n" +
                "    </mh:Document>\n";
    }

    private static String notat1_1() {
        return "    <mh:Document>\n" +
                "        <mh:DocumentConnection DN=\"Hoveddokument\" V=\"H\"/>\n" +
                "        <mh:RefDoc>\n" +
                "            <mh:IssueDate V=\"2017-09-29\"/>\n" +
                "            <mh:MsgType DN=\"XML-instans\" V=\"XML\"/>\n" +
                "            <mh:MimeType>text/xml</mh:MimeType>\n" +
                "            <mh:Content>\n" +
                "                <dia:Dialogmelding>\n" +
                "                    <dia:Notat>\n" +
                "                        <dia:TemaKodet/>\n" +
                "                        <dia:DokIdNotat>dokidnotat1_1</dia:DokIdNotat>\n" +
                "                    </dia:Notat>\n" +
                "                </dia:Dialogmelding>\n" +
                "            </mh:Content>\n" +
                "        </mh:RefDoc>\n" +
                "    </mh:Document>\n";
    }

    private static String vedlegg() {
        return "    <mh:Document>\n" +
                "        <mh:DocumentConnection DN=\"Vedlegg\" V=\"V\"/>\n" +
                "        <mh:RefDoc>\n" +
                "            <mh:IssueDate V=\"2017-09-29\"/>\n" +
                "            <mh:MsgType DN=\"Vedlegg\" V=\"A\"/>\n" +
                "            <mh:MimeType>application/pdf</mh:MimeType>\n" +
                "            <mh:Content>\n" +
                "                <b64c:Base64Container>vedlegg</b64c:Base64Container>\n" +
                "            </mh:Content>\n" +
                "        </mh:RefDoc>\n" +
                "    </mh:Document>\n";
    }
}