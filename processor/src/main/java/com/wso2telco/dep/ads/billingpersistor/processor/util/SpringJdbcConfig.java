package com.wso2telco.dep.ads.billingpersistor.processor.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

@Configuration
public class SpringJdbcConfig {

    @Value("${c3p0.driver.class}")
    private String driverClass;
    @Value("${c3p0.jdbc.url}")
    private String jdbcUrl;
    @Value("${c3p0.user}")
    private String user;
    @Value("${c3p0.password}")
    private String password;
    @Value("${c3p0.min.pool.size}")
    private int minPoolSize;
    @Value("${c3p0.max.pool.size}")
    private int maxPoolSize;
    @Value("${c3p0.max.idle.time}")
    private int maxConnectionAge;
    @Value("${c3p0.acquire.increment}")
    private int acquireIncrement;
    @Value("${c3p0.max.idle.time}")
    private int maxIdleTime;

    @Bean
    public ComboPooledDataSource getDataSource() throws PropertyVetoException {


        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMaxIdleTime(maxIdleTime);
        dataSource.setMaxConnectionAge(maxConnectionAge);
        dataSource.setAcquireIncrement(acquireIncrement);

        return dataSource;
    }
}
