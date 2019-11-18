package com.endava.bootifuljms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJms
@EnableAsync
@Slf4j
public class BootifulJmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootifulJmsApplication.class, args);
    }
}