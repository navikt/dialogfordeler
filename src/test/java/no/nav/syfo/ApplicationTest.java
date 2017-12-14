package no.nav.syfo;

import no.nav.syfo.props.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApplicationTest {
    @Inject
    private MqChannelProperties mqChannelProperties;
    @Inject
    private MqDialogmeldingerProperties mqDialogmeldingerProperties;
    @Inject
    private MqEiaQueueMottakInboundProperties mqEiaQueueMottakInboundProperties;
    @Inject
    private MqEiaQueueMottakOutboundProperties mqEiaQueueMottakOutboundProperties;
    @Inject
    private MqGatewayProperties mqGatewayProperties;
    @Inject
    private SrvAppserverProperties srvAppserverProperties;
    @Inject
    private ToggleProperties toggleProperties;


    @Test
    public void test() {
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(mqChannelProperties.getName()).isEqualTo("CHANNEL_NAME");
        soft.assertThat(mqDialogmeldingerProperties.getQueuename()).isEqualTo("DIALOGMELDINGER");
        soft.assertThat(mqEiaQueueMottakInboundProperties.getQueuename()).isEqualTo("EIA_QUEUE_MOTTAK_INBOUND");
        soft.assertThat(mqEiaQueueMottakOutboundProperties.getQueuename()).isEqualTo("EIA_QUEUE_MOTTAK_OUTBOUND");
        soft.assertThat(mqGatewayProperties.getName()).isEqualTo("NAME");
        soft.assertThat(mqGatewayProperties.getHostname()).isEqualTo("HOSTNAME");
        soft.assertThat(mqGatewayProperties.getPort()).isEqualTo(1000);
        soft.assertThat(srvAppserverProperties.getUsername()).isEqualTo("srvappserver_username");
        soft.assertThat(srvAppserverProperties.getPassword()).isEqualTo("srvappserver_password");
        soft.assertThat(toggleProperties.isLeggMeldingerPaKo()).isTrue();
        soft.assertAll();
    }
}