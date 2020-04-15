//package com.endava.bootifuljms;
//
//import com.endava.bootifuljms.serpinski.SerpinnskiTriangleCalculator;
//import com.endava.bootifuljms.serpinski.SerpinskiRequest;
//import com.endava.bootifuljms.serpinski.SerpinskiResponse;
//import com.endava.bootifuljms.util.MessageUtil;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//import javax.jms.*;
//
//@Slf4j
//public class JmsAsyncReceiver implements MessageListener {
//
//    private final Connection connection;
//    private final Session session;
//
//    @SneakyThrows
//    public JmsAsyncReceiver() {
//        ConnectionFactory connectionFactory =
//                new ActiveMQConnectionFactory("tcp://localhost:61616");
//        connection = connectionFactory.createConnection();
//        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue = session.createQueue("REQUEST.Q");
//        MessageConsumer consumer = session.createConsumer(queue);
//        consumer.setMessageListener(this);
//    }
//
//    @SneakyThrows
//    public void start() {
//        connection.start();
//    }
//
//    @SneakyThrows
//    @Override
//    public void onMessage(Message msg) {
//        TextMessage requestMessage = (TextMessage) msg;
//        SerpinskiRequest request = MessageUtil.parseRequestMessageFromString(requestMessage.getText());
//        log.info("Received requestMessage");
//        String triangle = calculateTriangle(request);
//
//        MessageProducer producer = session.createProducer(requestMessage.getJMSReplyTo());
//        TextMessage responseMessage = session.createTextMessage(triangle);
//        responseMessage.setJMSCorrelationID(requestMessage.getJMSMessageID());
//        producer.send(responseMessage);
//        log.info("Response was sent");
//    }
//
//    private String calculateTriangle(SerpinskiRequest request) {
//        SerpinnskiTriangleCalculator calculator = new SerpinnskiTriangleCalculator();
//        SerpinskiResponse response = calculator.calculate(request);
//        return response.getValue();
//    }
//
//    public static void main(String[] args) {
//        JmsAsyncReceiver jmsAsyncReceiver = new JmsAsyncReceiver();
//        jmsAsyncReceiver.start();
//    }
//}
