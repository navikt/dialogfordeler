package no.nav.syfo.config;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQXAConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.ibm.msg.client.wmq.v6.base.internal.MQC;
import no.nav.syfo.jms.UserCredentialsXaConnectionFactoryAdapter;
import no.nav.syfo.props.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
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

    /**
     * QA.T1_DIALOGFORDELER.DIALOGMELDINGER
     */
    @Bean
    public Queue dialogmeldingerQueue(MqDialogmeldingerProperties queue) throws JMSException {
        return new MQQueue(queue.getQueuename());
    }

    /**
     * QA.T414.FS06_EIA_MELDINGER
     */
    @Bean
    public JmsTemplate jmsEiaQueueMottakInbound(Queue eiaQueueMottakInbound, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(eiaQueueMottakInbound);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean
    public Queue eiaQueueMottakInbound(MqEiaQueueMottakInboundProperties queue) throws JMSException {
        return new MQQueue(queue.getQueuename());
    }

    /**
     * QA.T414.IU03_KVITTERING
     */
    @Bean
    public JmsTemplate jmsEiaQueueMottakOutbound(Queue eiaQueueMottakOutbound, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(eiaQueueMottakOutbound);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean
    public Queue eiaQueueMottakOutbound(MqEiaQueueMottakOutboundProperties queue) throws JMSException {
        return new MQQueue(queue.getQueuename());
    }

    /**
     * QA.Q414.IU03_UTSENDING
     */
    @Bean
    public JmsTemplate jmsEiaQueueMottakMeldingOutbound(Queue eiaQueueMottakMeldingOutbound, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(eiaQueueMottakMeldingOutbound);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean
    public Queue eiaQueueMottakMeldingOutbound(MqEiaQueueMottakMeldingOutboundProperties queue) throws JMSException {
        return new MQQueue(queue.getQueuename());
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
    public PlatformTransactionManager platformTransactionManager(ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }

    @Bean
    public DestinationResolver destinationResolver(ApplicationContext context) {
        return (session, destinationName, pubSubDomain) -> context.getBean(destinationName, Queue.class);
    }

    @Bean
    public ConnectionFactory connectionFactory(MqGatewayProperties mqGateway, MqChannelProperties mqChannel, SrvAppserverProperties srvAppserver) throws JMSException {
        MQXAConnectionFactory connectionFactory = new MQXAConnectionFactory();
        connectionFactory.setHostName(mqGateway.getHostname());
        connectionFactory.setPort(mqGateway.getPort());
        connectionFactory.setChannel(mqChannel.getName());
        connectionFactory.setQueueManager(mqGateway.getName());
        connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        connectionFactory.setCCSID(UTF_8_WITH_PUA);
        connectionFactory.setIntProperty(WMQConstants.JMS_IBM_ENCODING, MQC.MQENC_NATIVE);
        connectionFactory.setIntProperty(WMQConstants.JMS_IBM_CHARACTER_SET, UTF_8_WITH_PUA);
        UserCredentialsXaConnectionFactoryAdapter adapter = new UserCredentialsXaConnectionFactoryAdapter();
        adapter.setTargetConnectionFactory(connectionFactory);
        adapter.setUsername(srvAppserver.getUsername());
        adapter.setPassword(srvAppserver.getPassword());
        return adapter;
    }
}
