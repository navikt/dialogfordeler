package no.nav.syfo.consumer.mq;

import no.nav.syfo.exception.MeldingInboundException;
import no.nav.syfo.service.MeldingRuter;
import org.junit.Before;
import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DialogmeldingerConsumerTest {
    private MeldingRuter meldingRuter;
    private DialogmeldingerConsumer dialogmeldingerConsumer;

    @Before
    public void setUp() {
        dialogmeldingerConsumer = new DialogmeldingerConsumer();
        meldingRuter = mock(MeldingRuter.class);
        dialogmeldingerConsumer.setMeldingRuter(meldingRuter);
    }

    @Test
    public void listen() throws Exception {
        final TextMessage mock = mock(TextMessage.class);
        when(mock.getText()).thenReturn("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<mh:MsgHead xmlns:b64c=\"http://www.kith.no/xmlstds/base64container\"\n" +
                "            xmlns:dia=\"http://www.kith.no/xmlstds/dialog/2013-01-23\"\n" +
                "            xmlns:dia2=\"http://www.kith.no/xmlstds/dialog/2006-10-11\"\n" +
                "            xmlns:mh=\"http://www.kith.no/xmlstds/msghead/2006-05-24\"\n" +
                "            xsi:schemaLocation=\"http://www.kith.no/xmlstds/msghead/2006-05-24 MsgHead-v1_2.xsd\"\n" +
                "            xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "</mh:MsgHead>");
        dialogmeldingerConsumer.listen(mock);

        verify(meldingRuter).evaluer(any(TextMessage.class));
    }

    @Test(expected = MeldingInboundException.class)
    public void listenJMSException() throws Exception {
        final TextMessage mock = mock(TextMessage.class);
        when(mock.getText()).thenReturn("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<mh:MsgHead xmlns:b64c=\"http://www.kith.no/xmlstds/base64container\"\n" +
                "            xmlns:dia=\"http://www.kith.no/xmlstds/dialog/2013-01-23\"\n" +
                "            xmlns:dia2=\"http://www.kith.no/xmlstds/dialog/2006-10-11\"\n" +
                "            xmlns:mh=\"http://www.kith.no/xmlstds/msghead/2006-05-24\"\n" +
                "            xsi:schemaLocation=\"http://www.kith.no/xmlstds/msghead/2006-05-24 MsgHead-v1_2.xsd\"\n" +
                "            xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "</mh:MsgHead>");
        doThrow(JMSException.class).when(meldingRuter).evaluer(any(TextMessage.class));
        dialogmeldingerConsumer.listen(mock);
    }
}