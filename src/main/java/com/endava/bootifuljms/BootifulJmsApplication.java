package com.endava.bootifuljms;

import com.endava.bootifuljms.serpinski.SerpinnskiTriangleCalculator;
import com.endava.bootifuljms.serpinski.SerpinskiRequest;
import com.endava.bootifuljms.serpinski.SerpinskiResponse;
import com.endava.bootifuljms.weather.report.Report;
import com.endava.bootifuljms.weather.repository.ReportRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableAsync
public class BootifulJmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootifulJmsApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public CommandLineRunner commandLineRunner(FooService fooService) {
        return args -> {
            fooService.sendMessage();
        };
    }

//    @Primary
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new JpaTransactionManager();
//    }
}

@Service
@RequiredArgsConstructor
class FooService {
    private final JmsTemplate jmsTemplate;
    private final ReportRepository reportRepository;

    @Transactional
    public void sendMessage() {


        jmsTemplate.convertAndSend("REQUEST.Q", new SerpinskiRequest(12));
        reportRepository.save(Report.builder()
                .fileName("file2")
                .creationTimestamp(LocalDateTime.now())
                .content(new byte[]{1,2 , 3})
                .build());

        if (true)
            throw null;
    }
}

@Component
@Slf4j
@RequiredArgsConstructor
class MessageSender {

    private final JmsTemplate jmsTemplate;

    @JmsTransaction
    public void sendMessage() {
        jmsTemplate.convertAndSend("REQUEST.Q", new SerpinskiRequest(32));
        log.info("Message 1 was sent!");

//        if (true)
//            throw new NullPointerException();

        jmsTemplate.convertAndSend("REQUEST.Q", new SerpinskiRequest(24));
        log.info("Message 2 was sent!");
    }
}

@Component
@Slf4j
class MessageListener {

    @JmsListener(destination = "REQUEST.Q")
    public void onMessage(SerpinskiRequest request) {
        SerpinnskiTriangleCalculator calculator = new SerpinnskiTriangleCalculator();
        SerpinskiResponse response = calculator.calculate(request);
        System.out.println(response.getValue());
    }

    @JmsListener(destination = "REQUEST2.Q")
    public void onMessage2(SerpinskiRequest request) {
        SerpinnskiTriangleCalculator calculator = new SerpinnskiTriangleCalculator();
        SerpinskiResponse response = calculator.calculate(request);
        System.out.println(response.getValue());
    }
}
