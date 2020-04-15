package com.endava.bootifuljms;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

@Slf4j
public class JmsReceiver {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");
        try (Connection connection = connectionFactory.createConnection()) {
            connection.start();
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue("REQUEST.Q");
            MessageConsumer consumer = session.createConsumer(queue);
            TextMessage message = (TextMessage) consumer.receive();
            log.info("Received message: {}", message.getText());

            message.acknowledge();;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
