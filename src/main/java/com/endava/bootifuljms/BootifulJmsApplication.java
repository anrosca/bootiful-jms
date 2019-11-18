package com.endava.bootifuljms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableJms
@EnableAsync
@Slf4j
public class BootifulJmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootifulJmsApplication.class, args);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    @Bean
    public JmsTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }

    @Bean
    public CommandLineRunner commandLineRunner(JmsTemplate jmsTemplate, FooService fooService) {
        return args -> {
            try {
                fooService.foo();
            } catch (Exception e) {
                log.info("Error in foo service", e);
            }
        };
    }
}

@Service
@RequiredArgsConstructor
@Slf4j
class FooService {
    private final JmsTemplate jmsTemplate;

    @Transactional
    public void foo() {
        log.info("Sending message #1");
        jmsTemplate.convertAndSend("foo.Q", "Message #1");
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        log.info("Sending message #2");
        jmsTemplate.convertAndSend("foo.Q", "Message #2");
    }
}

@Component
@Slf4j
class ActivemqListener {

    @JmsListener(destination = "foo.Q")
//    @SendTo("bar.Q")
    public void onMessage(TextMessage payload) throws JMSException {
        log.info("Received message: {}", payload.getText());
//        return "Processed: " + payload;
//        return MessageBuilder.withPayload("processed: " + payload.getText())
//                .setHeader("yay", "bbaas").build();
//        return JmsResponse.forQueue("processed: " + payload.getText(), "yay.Q");
    }
}


