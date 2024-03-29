package no.nav.syfo.web.rest.dialogmelding;

import no.nav.syfo.provider.mq.MottakQueueUtsendingProvider;
import no.nav.syfo.repository.MeldingLoggRepository;
import no.nav.syfo.web.rest.dialogmelding.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static no.nav.syfo.domain.enums.FellesformatType.SYFO_MELDING;
import static no.nav.syfo.domain.enums.MeldingLoggType.UTAAENDE_MELDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DialogmeldingServiceTest {
    @Mock
    private DialogmeldingRespository dialogmeldingRespository;
    @Mock
    private MeldingLoggRepository meldingLoggRepository;
    @Mock
    private MottakQueueUtsendingProvider mottakQueue;
    @InjectMocks
    private DialogmeldingService dialogmeldingService;

    @Test
    public void registrerDialogmelding() {
        dialogmeldingService.registrerDialogmelding(hodemelding());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(dialogmeldingRespository).registrerDialogmelding(anyString(), anyString(), eq(SYFO_MELDING));
        verify(mottakQueue).sendTilEMottak(argumentCaptor.capture());
        verify(meldingLoggRepository).loggMelding(anyString(), anyLong(), eq(UTAAENDE_MELDING));

        assertThat(argumentCaptor.getValue())
                .contains("<EI_fellesformat xmlns=\"http://www.nav.no/xml/eiff/2/\" xmlns:ns6=\"http://www.kith.no/xmlstds/base64container\" xmlns:ns5=\"http://www.kith.no/xmlstds/felleskomponent1\" xmlns:ns2=\"http://www.kith.no/xmlstds/msghead/2006-05-24\" xmlns:ns4=\"http://www.kith.no/xmlstds/dialog/2006-10-11\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                        "    <ns2:MsgHead>\n" +
                        "        <ns2:MsgInfo>\n" +
                        "            <ns2:Type V=\"DIALOG_NOTAT\" DN=\"Notat\"/>\n" +
                        "            <ns2:MIGversion>v1.2 2006-05-24</ns2:MIGversion>\n")
                .containsPattern("<ns2:GenDate>\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{0,7}</ns2:GenDate>")
                .containsPattern("<ns2:MsgId>[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}</ns2:MsgId>")
                .contains("            <ns2:Ack V=\"J\" DN=\"Ja\"/>\n" +
                        "            <ns2:Sender>\n" +
                        "                <ns2:Organisation>\n" +
                        "                    <ns2:OrganisationName>NAV</ns2:OrganisationName>\n" +
                        "                    <ns2:Ident>\n" +
                        "                        <ns2:Id>889640782</ns2:Id>\n" +
                        "                        <ns2:TypeId V=\"ENH\" S=\"2.16.578.1.12.4.1.1.9051\" DN=\"Organisasjonsnummeret i Enhetsregisteret\"/>\n" +
                        "                    </ns2:Ident>\n" +
                        "                    <ns2:Ident>\n" +
                        "                        <ns2:Id>79768</ns2:Id>\n" +
                        "                        <ns2:TypeId V=\"HER\" S=\"2.16.578.1.12.4.1.1.9051\" DN=\"Identifikator fra Helsetjenesteenhetsregisteret (HER-id)\"/>\n" +
                        "                    </ns2:Ident>\n" +
                        "                </ns2:Organisation>\n" +
                        "            </ns2:Sender>\n" +
                        "            <ns2:Receiver>\n" +
                        "                <ns2:Organisation>\n" +
                        "                    <ns2:OrganisationName>navn</ns2:OrganisationName>\n" +
                        "                    <ns2:Ident>\n" +
                        "                        <ns2:Id>partnerId</ns2:Id>\n" +
                        "                        <ns2:TypeId V=\"HER\" S=\"2.16.578.1.12.4.1.1.9051\" DN=\"Identifikator fra Helsetjenesteenhetsregisteret (HER-id)\"/>\n" +
                        "                    </ns2:Ident>\n" +
                        "                    <ns2:Ident>\n" +
                        "                        <ns2:Id>orgnummer</ns2:Id>\n" +
                        "                        <ns2:TypeId V=\"ENH\" S=\"2.16.578.1.12.4.1.1.9051\" DN=\"Organisasjonsnummeret i Enhetsregisteret\"/>\n" +
                        "                    </ns2:Ident>\n" +
                        "                    <ns2:Address>\n" +
                        "                        <ns2:Type V=\"RES\" DN=\"Besøksadresse\"/>\n" +
                        "                        <ns2:StreetAdr>adresse</ns2:StreetAdr>\n" +
                        "                        <ns2:PostalCode>postnummer</ns2:PostalCode>\n" +
                        "                        <ns2:City>poststed</ns2:City>\n" +
                        "                    </ns2:Address>\n" +
                        "                    <ns2:HealthcareProfessional>\n" +
                        "                        <ns2:RoleToPatient V=\"6\" S=\"2.16.578.1.12.4.1.1.9034\" DN=\"Fastlege\"/>\n" +
                        "                        <ns2:FamilyName>etternavn</ns2:FamilyName>\n" +
                        "                        <ns2:MiddleName>mellomnavn</ns2:MiddleName>\n" +
                        "                        <ns2:GivenName>fornavn</ns2:GivenName>\n" +
                        "                        <ns2:Ident>\n" +
                        "                            <ns2:Id>fnr</ns2:Id>\n" +
                        "                            <ns2:TypeId V=\"FNR\" S=\"2.16.578.1.12.4.1.1.8116\" DN=\"Fødselsnummer Norsk fødselsnummer\"/>\n" +
                        "                        </ns2:Ident>\n" +
                        "                        <ns2:Ident>\n" +
                        "                            <ns2:Id>hprId</ns2:Id>\n" +
                        "                            <ns2:TypeId V=\"HPR\" S=\"2.16.578.1.12.4.1.1.8116\" DN=\"HPR-nummer\"/>\n" +
                        "                        </ns2:Ident>\n" +
                        "                    </ns2:HealthcareProfessional>\n" +
                        "                </ns2:Organisation>\n" +
                        "            </ns2:Receiver>\n" +
                        "            <ns2:Patient>\n" +
                        "                <ns2:FamilyName>etternavn</ns2:FamilyName>\n" +
                        "                <ns2:MiddleName>mellomnavn</ns2:MiddleName>\n" +
                        "                <ns2:GivenName>fornavn</ns2:GivenName>\n" +
                        "                <ns2:Ident>\n" +
                        "                    <ns2:Id>fnr</ns2:Id>\n" +
                        "                    <ns2:TypeId V=\"FNR\" S=\"2.16.578.1.12.4.1.1.8116\" DN=\"Fødselsnummer\"/>\n" +
                        "                </ns2:Ident>\n" +
                        "            </ns2:Patient>\n" +
                        "        </ns2:MsgInfo>\n" +
                        "        <ns2:Document>\n" +
                        "            <ns2:DocumentConnection V=\"H\" DN=\"Hoveddokument\"/>\n" +
                        "            <ns2:RefDoc>\n")
                .containsPattern("<ns2:IssueDate V=\"\\d{4}-\\d{2}-\\d{2}\"/>")
                .contains("                <ns2:MsgType V=\"XML\" DN=\"XML-instans\"/>\n" +
                        "                <ns2:MimeType>text/xml</ns2:MimeType>\n" +
                        "                <ns2:Content>\n" +
                        "                    <ns4:Dialogmelding>\n" +
                        "                        <ns4:Notat>\n" +
                        "                            <ns4:TemaKodet V=\"1\" S=\"2.16.578.1.12.4.1.1.8127\" DN=\"Oppfølgingsplan\"/>\n" +
                        "                            <ns4:TekstNotatInnhold xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">Åpne PDF-vedlegg</ns4:TekstNotatInnhold>")
                .containsPattern("<ns4:DokIdNotat>[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}</ns4:DokIdNotat>")
                .contains("                            <ns4:RollerRelatertNotat>\n" +
                        "                                <ns4:RolleNotat V=\"1\" S=\"2.16.578.1.12.4.1.1.9057\"/>\n" +
                        "                                <ns4:Person/>\n" +
                        "                            </ns4:RollerRelatertNotat>\n" +
                        "                        </ns4:Notat>\n" +
                        "                    </ns4:Dialogmelding>\n" +
                        "                </ns2:Content>\n" +
                        "            </ns2:RefDoc>\n" +
                        "        </ns2:Document>\n" +
                        "        <ns2:Document>\n" +
                        "            <ns2:DocumentConnection V=\"V\" DN=\"Vedlegg\"/>\n" +
                        "            <ns2:RefDoc>\n")
                .containsPattern("<ns2:IssueDate V=\"\\d{4}-\\d{2}-\\d{2}\"/>")
                .contains("                <ns2:MsgType V=\"A\" DN=\"Vedlegg\"/>\n" +
                        "                <ns2:MimeType>application/pdf</ns2:MimeType>\n" +
                        "                <ns2:Content>\n" +
                        "                    <ns6:Base64Container>AAECAwQFBgcICQ==</ns6:Base64Container>\n" +
                        "                </ns2:Content>\n" +
                        "            </ns2:RefDoc>\n" +
                        "        </ns2:Document>\n" +
                        "    </ns2:MsgHead>\n" +
                        "    <MottakenhetBlokk partnerReferanse=\"herId\" ebRole=\"Saksbehandler\" ebService=\"Oppfolgingsplan\" ebAction=\"Plan\"/>\n" +
                        "</EI_fellesformat>");
    }

    private RSHodemelding hodemelding() {
        return new RSHodemelding(
                new RSMeldingInfo(
                        new RSMottaker("herId",
                                "partnerId",
                                "orgnummer",
                                "navn",
                                "adresse",
                                "postnummer",
                                "poststed",
                                new RSBehandler(
                                        "fnr",
                                        "hprId",
                                        "fornavn",
                                        "mellomnavn",
                                        "etternavn")),
                        new RSPasient(
                                "fnr",
                                "fornavn",
                                "mellomnavn",
                                "etternavn")),
                new RSVedlegg(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    }
}
