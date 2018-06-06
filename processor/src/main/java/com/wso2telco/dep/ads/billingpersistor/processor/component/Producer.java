package com.wso2telco.dep.ads.billingpersistor.processor.component;

import com.wso2telco.dep.ads.billingpersistor.processor.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentLinkedQueue;


@RestController
@RequestMapping("/processor")
public class Producer {

    @Autowired
    private ConcurrentLinkedQueue<Record> queue;

    @RequestMapping(value = "/produce", method = RequestMethod.POST)
    public ResponseEntity push(@RequestBody Record record) {

        boolean isAdded = queue.add(record);
        if (isAdded) {
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
