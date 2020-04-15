package com.endava.bootifuljms;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Transactional("jmsTransactionManager1")
@Retention(RetentionPolicy.RUNTIME)
public @interface JmsTransaction {}
