package com.endava.bootifuljms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class JmsReceiver {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try(Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            connection.start();
            Queue queue = session.createQueue("ORDERS.Q");
            MessageConsumer consumer = session.createConsumer(queue);

            TextMessage textMessage = (TextMessage) consumer.receive();
            System.out.println("Received message: " + textMessage.getText());
            System.out.println("Message header: " + textMessage.getStringProperty("CUSTOMER_NAME"));

            TimeUnit.SECONDS.sleep(10);

            if (true)
                throw new NullPointerException();

            TextMessage secondMessage = (TextMessage) consumer.receive();
            System.out.println("Received message: " + secondMessage.getText());

            session.commit();
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
