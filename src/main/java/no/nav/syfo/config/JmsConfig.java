package no.nav.syfo.config;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQXAConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.ibm.msg.client.wmq.v6.base.internal.MQC;
import no.nav.syfo.jms.UserCredentialsXaConnectionFactoryAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.XAConnectionFactoryWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

@Configuration
@EnableJms
public class JmsConfig {
    private static final int UTF_8_WITH_PUA = 1208;

    @Value("${DIALOGFORDELER_DIALOGMELDINGER_QUEUENAME}")
    private String dialogmeldingerQueuename;
    @Value("${MOTTAK_QUEUE_EIA2_MELDINGER_QUEUENAME}")
    private String mottakQueueEia2MeldingerQueuename;
    @Value("${MOTTAK_QUEUE_UTSENDING_QUEUENAME}")
    private String mottakQueueUtsendingQueuename;
    @Value("${DIALOGFORDELER_CHANNEL_NAME}")
    private String channelName;
    @Value("${MQGATEWAY03_HOSTNAME}")
    private String gatewayHostname;
    @Value("${MQGATEWAY03_NAME}")
    private String gatewayName;
    @Value("${MQGATEWAY03_PORT}")
    private int gatewayPort;
    @Value("${SRVAPPSERVER_USERNAME}")
    private String srvAppserverUsername;
    @Value("${SRVAPPSERVER_PASSWORD}")
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

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory, DestinationResolver destinationResolver, PlatformTransactionManager platformTransactionManager) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDestinationResolver(destinationResolver);
        factory.setConcurrency("3-10");
        factory.setTransactionManager(platformTransactionManager);
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
        connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        connectionFactory.setCCSID(UTF_8_WITH_PUA);
        connectionFactory.setIntProperty(WMQConstants.JMS_IBM_ENCODING, MQC.MQENC_NATIVE);
        connectionFactory.setIntProperty(WMQConstants.JMS_IBM_CHARACTER_SET, UTF_8_WITH_PUA);
        UserCredentialsXaConnectionFactoryAdapter adapter = new UserCredentialsXaConnectionFactoryAdapter();
        adapter.setTargetConnectionFactory(connectionFactory);
        adapter.setUsername(srvAppserverUsername);
        adapter.setPassword(srvAppserverPassword);
        return xaConnectionFactoryWrapper.wrapConnectionFactory(adapter);
    }
}
