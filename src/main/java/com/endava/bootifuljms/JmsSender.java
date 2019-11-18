package com.endava.bootifuljms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsSender {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");
        try (Connection connection = connectionFactory.createConnection()) {
            connection.start();
            try (Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
                Queue queue = session.createQueue("EM_ORDERS.Q");
                MessageProducer producer = session.createProducer(queue);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                TextMessage textMessage = session.createTextMessage();
                String payload = "hjhjhjhj";
                textMessage.setText(payload);
                producer.send(textMessage);
            }
        } catch (JMSException e) {
           throw new RuntimeException(e);
        }
    }
}
