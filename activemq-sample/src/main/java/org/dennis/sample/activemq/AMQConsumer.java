package org.dennis.sample.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * @author deng.zhang
 * @since 1.0.0
 */
public class AMQConsumer {
    private Connection connection;
    private Session session;
    private MessageConsumer consumer;

    public AMQConsumer() throws JMSException {
        AMQConfig config = new AMQConfig();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                config.getUser(),
                config.getPassword(),
                config.getBrokerUrl());
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("activemq_sample_queue");
        consumer = session.createConsumer(destination);
        consumer.setMessageListener(new AMQListener());
    }


    public void close() throws JMSException {
        if (consumer != null) {
            consumer.close();
        }
        if (session != null) {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws JMSException {
        AMQConsumer consumer = new AMQConsumer();
    }
}
