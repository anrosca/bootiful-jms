package com.endava.bootifuljms.config;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

public class AtomikosJtaPlatform extends AbstractJtaPlatform {

    private static final long serialVersionUID = 1L;

    private static TransactionManager transactionManager;

    private static UserTransaction transaction;

    @Override
    protected TransactionManager locateTransactionManager() {
        return transactionManager;
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return transaction;
    }

    public static void setTransactionManager(final TransactionManager transactionManager) {
        AtomikosJtaPlatform.transactionManager = transactionManager;
    }

    public static void setTransaction(final UserTransaction transaction) {
        AtomikosJtaPlatform.transaction = transaction;
    }
}
