package com.endava.bootifuljms.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.jms.ConnectionFactory;
import javax.jms.XAConnectionFactory;
import javax.sql.XADataSource;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class TransactionConfiguration {

    static class DbConfig {
        @Primary
        @ConfigurationProperties("spring.datasource")
        @Bean
        public DataSourceProperties dataSourceProperties() {
            return new DataSourceProperties();
        }

        @Primary
        @Bean
        public XADataSource safeStoreDataSource(DataSourceProperties dataSourceProperties) throws SQLException {
            PGXADataSource dataSource = new PGXADataSource();
            dataSource.setUser(dataSourceProperties.getUsername());
            dataSource.setPassword(dataSourceProperties.getPassword());
            dataSource.setUrl(dataSourceProperties.getUrl());
            return dataSource;
        }

        @Bean
        public AtomikosDataSourceBean safeStoreDbAtomikosDataSourceBean(@Qualifier("safeStoreDataSource") XADataSource dataSource) {
            AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
            dataSourceBean.setXaDataSource(dataSource);
            dataSourceBean.setUniqueResourceName("SAFESTORE_DB");
            return dataSourceBean;
        }

        @DependsOn({"transactionManager", "atomikosJtaPlatform"})
        @Primary
        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("safeStoreDbAtomikosDataSourceBean") AtomikosDataSourceBean dataSource) {
            LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
            factoryBean.setJtaDataSource(dataSource);
            factoryBean.setPackagesToScan("com.endava.bootifuljms.weather.report");
            factoryBean.setPersistenceUnitName("PERSISTENCE_UNIT");
            factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
            factoryBean.setJpaPropertyMap(makeJpaProperties());
            factoryBean.afterPropertiesSet();
            return factoryBean;
        }

        private Map<String, Object> makeJpaProperties() {
            Map<String, Object> properties = new HashMap<>();
            properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
            properties.put("javax.persistence.transactionType", "JTA");
            properties.put("hibernate.hbm2ddl.auto", "validate");
            return properties;
        }

        @Bean
        public JpaVendorAdapter jpaVendorAdapter() {
            HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
            adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
            return adapter;
        }
    }

    static class JmsConfig {

        @Bean
        public XAConnectionFactory reportingAQDataSource() {
            ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory();
            connectionFactory.setBrokerURL("tcp://localhost:61616");
            return connectionFactory;
        }

        @Bean
        public AtomikosConnectionFactoryBean aqAtomikosDataSourceBean(@Qualifier("reportingAQDataSource") XAConnectionFactory connectionFactory) {
            AtomikosConnectionFactoryBean dataSourceBean = new AtomikosConnectionFactoryBean();
            dataSourceBean.setXaConnectionFactory(connectionFactory);
            dataSourceBean.setUniqueResourceName("ORACLE_AQ");
            return dataSourceBean;
        }
    }

    @Bean
    public UserTransaction userTransaction() throws SystemException {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
         userTransactionImp.setTransactionTimeout(50_000);
        return userTransactionImp;
    }

    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean
    public AtomikosJtaPlatform atomikosJtaPlatform(UserTransactionManager userTransactionManager, UserTransaction userTransaction) {
        AtomikosJtaPlatform.setTransaction(userTransactionManager);
        AtomikosJtaPlatform.setTransactionManager(userTransactionManager);
        return new AtomikosJtaPlatform();
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(UserTransaction userTransaction, TransactionManager atomikosTransactionManager) {
        return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
    }
}
