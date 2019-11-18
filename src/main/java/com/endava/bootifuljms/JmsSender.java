package com.endava.bootifuljms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class JmsSender {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE)) {
            connection.start();
            Queue queue = session.createQueue("ORDERS.Q");
            MessageProducer producer = session.createProducer(queue);

            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("<?xml version='1.0' encoding='utf-8'>" +
                    "<order customer='honda'><id>1</id>" +
                    "<productId>13243243</productId>" +
                    "<amount>1</amount>" +
                    "</order>");
            textMessage.setStringProperty("CUSTOMER_NAME", "Honda");
            producer.send(textMessage);
            System.out.println("Send messsage #1");

            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            if (true)
//                throw new NullPointerException();

            TextMessage secondMessage = session.createTextMessage();
            secondMessage.setText("<?xml version='1.0' encoding='utf-8'>" +
                    "<order customer='toyota'><id>1</id>" +
                    "<productId>dssdsds</productId>" +
                    "<amount>2</amount>" +
                    "</order>");
            producer.send(secondMessage);
            System.out.println("Send messsage #2");

            session.commit();
            System.out.println("Message was sent!!!");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
