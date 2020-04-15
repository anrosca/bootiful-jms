package com.endava.bootifuljms;

import com.endava.bootifuljms.serpinski.SerpinskiRequest;
import com.endava.bootifuljms.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.w3c.dom.Text;

import javax.jms.*;

@Slf4j
public class JmsSender {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");
        try (Connection connection = connectionFactory.createConnection()) {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue requestQueue = session.createQueue("REQUEST.Q");
            Queue responseQueue = session.createQueue("RESPONSE.Q");
            MessageProducer producer = session.createProducer(requestQueue);
            String request = MessageUtil.createRequestMessageWithDepth(32);

            TextMessage requestMessage = session.createTextMessage(request);
            requestMessage.setJMSReplyTo(responseQueue);
            requestMessage.setStringProperty("_type", "com.endava.bootifuljms.serpinski.SerpinskiRequest");
            producer.send(requestMessage);

            MessageConsumer consumer = session.createConsumer(responseQueue,
                    "JMSCorrelationID='" + requestMessage.getJMSMessageID()  +"'");
            TextMessage responseMessage = (TextMessage) consumer.receive();
            String triangle = responseMessage.getText();
            System.out.println(triangle);
//            session.commit();
            log.info("Message was sent");
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
