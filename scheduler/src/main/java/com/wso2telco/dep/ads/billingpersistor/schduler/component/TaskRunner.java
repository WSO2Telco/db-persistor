package com.wso2telco.dep.ads.billingpersistor.schduler.component;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class TaskRunner {

    @Value("${processor.host}")
    private String processorHost;

    @Value("${consume.endpoint}")
    private String consumeEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Logger logger;

    @Value("${consume.batch.size}")
    private int batchSize;

    @Scheduled(cron = "${call.processor.schedule.cron}")
    public void callProcessor() {

        UUID correlator = UUID.randomUUID();
        String url = processorHost + consumeEndpoint + "/batchSize/" + batchSize + "/correlator/" + correlator;

        logger.info("Consuming batch [{}]", correlator);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null, String.class);

        logger.info("Processor returned with status code [ {} ]", responseEntity.getStatusCodeValue());
    }
}

