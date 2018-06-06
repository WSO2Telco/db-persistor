package com.wso2telco.dep.ads.billingpersistor.processor.util;

import com.wso2telco.dep.ads.billingpersistor.processor.model.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
public class Config {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ConcurrentLinkedQueue<Record> getQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    @Bean
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
