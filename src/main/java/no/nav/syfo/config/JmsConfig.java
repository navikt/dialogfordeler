package no.nav.syfo.config;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQXAConnectionFactory;
import no.nav.syfo.jms.UserCredentialsXaConnectionFactoryAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jms.XAConnectionFactoryWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

import static com.ibm.mq.constants.CMQC.MQENC_NATIVE;
import static com.ibm.msg.client.wmq.WMQConstants.*;

@Configuration
@EnableJms
@Profile({"remote", "local"})
public class JmsConfig {
    private static final int UTF_8_WITH_PUA = 1208;

    @Value("${dialogfordeler.dialogmeldinger.queuename}")
    private String dialogmeldingerQueuename;
    @Value("${mottak.queue.eia2.meldinger.queuename}")
    private String mottakQueueEia2MeldingerQueuename;
    @Value("${mottak.queue.utsending.queuename}")
    private String mottakQueueUtsendingQueuename;
    @Value("${mottak.queue.ebrev.kvittering.queuename}")
    private String mottakQueueEbrevKvitteringQueuename;
    @Value("${dialogfordeler.channel.name}")
    private String channelName;
    @Value("${mqgateway03.hostname}")
    private String gatewayHostname;
    @Value("${mqgateway03.name}")
    private String gatewayName;
    @Value("${mqgateway03.port}")
    private int gatewayPort;
    @Value("${srvappserver.username}")
    private String srvAppserverUsername;
    @Value("${srvappserver.password}")
    private String srvAppserverPassword;

    /**
     * QA.T1_DIALOGFORDELER.DIALOGMELDINGER
     */
    @Bean
    public Queue dialogmeldingerQueue() throws JMSException {
        return new MQQueue(dialogmeldingerQueuename);
    }

    /**
     * QA.T414.FS06_EIA_MELDINGER
     */
    @Bean
    public JmsTemplate jmsMottakQueueEia2Meldinger(Queue mottakQueueEia2Meldinger, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(mottakQueueEia2Meldinger);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean
    public Queue mottakQueueEia2Meldinger() throws JMSException {
        return new MQQueue(mottakQueueEia2MeldingerQueuename);
    }

    /**
     * QA.Q414.IU03_UTSENDING
     */
    @Bean
    public JmsTemplate jmsMottakQueueUtsending(Queue mottakQueueUtsending, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(mottakQueueUtsending);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean
    public Queue mottakQueueUtsending() throws JMSException {
        return new MQQueue(mottakQueueUtsendingQueuename);
    }

    /**
     * QA.Q414.IU03_EBREV_KVITTERING
     */
    @Bean
    public JmsTemplate jmsMottakQueueEbrevKvittering(Queue mottakQueueEbrevKvittering, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(mottakQueueEbrevKvittering);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean
    public Queue mottakQueueEbrevKvittering() throws JMSException {
        return new MQQueue(mottakQueueEbrevKvitteringQueuename);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory, DestinationResolver destinationResolver, PlatformTransactionManager platformTransactionManager, ErrorHandler errorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDestinationResolver(destinationResolver);
        factory.setConcurrency("3-10");
        factory.setTransactionManager(platformTransactionManager);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public DestinationResolver destinationResolver(ApplicationContext context) {
        return (session, destinationName, pubSubDomain) -> context.getBean(destinationName, Queue.class);
    }

    @Bean
    public ConnectionFactory connectionFactory(XAConnectionFactoryWrapper xaConnectionFactoryWrapper) throws Exception {
        MQXAConnectionFactory connectionFactory = new MQXAConnectionFactory();
        connectionFactory.setHostName(gatewayHostname);
        connectionFactory.setPort(gatewayPort);
        connectionFactory.setChannel(channelName);
        connectionFactory.setQueueManager(gatewayName);
        connectionFactory.setTransportType(WMQ_CM_CLIENT);
        connectionFactory.setCCSID(UTF_8_WITH_PUA);
        connectionFactory.setIntProperty(JMS_IBM_ENCODING, MQENC_NATIVE);
        connectionFactory.setIntProperty(JMS_IBM_CHARACTER_SET, UTF_8_WITH_PUA);
        UserCredentialsXaConnectionFactoryAdapter adapter = new UserCredentialsXaConnectionFactoryAdapter();
        adapter.setTargetConnectionFactory(connectionFactory);
        adapter.setUsername(srvAppserverUsername);
        adapter.setPassword(srvAppserverPassword);
        return xaConnectionFactoryWrapper.wrapConnectionFactory(adapter);
    }
}
