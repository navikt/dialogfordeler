package no.nav.syfo.config;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQXAConnectionFactory;
import no.nav.syfo.jms.UserCredentialsXaConnectionFactoryAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Value("${mottak.queue.padm2.meldinger.queuename}")
    private String mottakQueuePadm2MeldingerQueuename;
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
    @Bean(name = "Eia2JmsTemplate")
    public JmsTemplate jmsMottakQueueEia2Meldinger(@Qualifier("Eia2Queue") Queue mottakQueueEia2Meldinger, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(mottakQueueEia2Meldinger);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean(name = "Eia2Queue")
    public Queue mottakQueueEia2Meldinger() throws JMSException {
        return new MQQueue(mottakQueueEia2MeldingerQueuename);
    }


    /**
     * QA.Q1_PADM.INPUT
     */
    @Bean(name = "Padm2JmsTemplate")
    public JmsTemplate jmsMottakQueuePadm2Meldinger(@Qualifier("Padm2Queue") Queue mottakQueuePadm2Meldinger, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(mottakQueuePadm2Meldinger);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }


    @Bean(name = "Padm2Queue")
    public Queue mottakQueuePadm2Meldinger() throws JMSException {
        return new MQQueue(mottakQueuePadm2MeldingerQueuename);
    }

    /**
     * QA.Q414.IU03_UTSENDING
     */
    @Bean(name = "UtsendingJmsTemplate")
    public JmsTemplate jmsMottakQueueUtsending(@Qualifier("UtsendingQueue") Queue mottakQueueUtsending, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(mottakQueueUtsending);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean(name = "UtsendingQueue")
    public Queue mottakQueueUtsending() throws JMSException {
        return new MQQueue(mottakQueueUtsendingQueuename);
    }

    /**
     * QA.Q414.IU03_EBREV_KVITTERING
     */
    @Bean(name = "EbrevJmsTemplate")
    public JmsTemplate jmsMottakQueueEbrevKvittering(@Qualifier("EbrevQueue") Queue mottakQueueEbrevKvittering, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(mottakQueueEbrevKvittering);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean(name = "EbrevQueue")
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
