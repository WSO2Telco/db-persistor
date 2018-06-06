package com.wso2telco.dep.ads.billingpersistor.schduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Scheduler {


    public static void main(String[] args) {
        SpringApplication.run(Scheduler.class, args);
    }
}
