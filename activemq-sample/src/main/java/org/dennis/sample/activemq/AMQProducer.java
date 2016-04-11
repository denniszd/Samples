package org.dennis.sample.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

/**
 * @author deng.zhang
 * @since 1.0.0
 */
public class AMQProducer {
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public AMQProducer() throws JMSException {
        AMQConfig config = new AMQConfig();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                config.getUser(),
                config.getPassword(),
                config.getBrokerUrl());
        connection = connectionFactory.createConnection();
        //打开连接
        connection.start();
        //创建会话，选择事务和ACK模式
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //根据指定的队列名称创建Destination（若队列不存在，ActiveMQ会创建一个新的队列）
        Destination destination = session.createQueue("activemq_sample_queue");
        //创建消息生产者
        producer = session.createProducer(destination);
        //消息传送模式是否为持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    public void send(String msg) throws JMSException {
        producer.send(session.createTextMessage(msg));
    }

    public void close() throws JMSException {
        if (producer != null) {
            producer.close();
        }
        if (session != null) {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws JMSException {
        AMQProducer producer = new AMQProducer();
        for (int i = 1; i <= 20; i++) {
            producer.send("测试消息" + i);
        }
    }
}
