package org.dennis.sample.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author deng.zhang
 * @since 1.0.0
 */
public class AMQListener implements MessageListener {
    private int count = 0;
    public void onMessage(Message message) {
        count++;
        try {
            System.out.println("第" + count + "条消息： " + ((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
