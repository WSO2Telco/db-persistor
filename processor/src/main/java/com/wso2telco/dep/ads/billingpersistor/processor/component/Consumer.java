package com.wso2telco.dep.ads.billingpersistor.processor.component;

import com.wso2telco.dep.ads.billingpersistor.processor.model.Record;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


@RestController
@RequestMapping("/processor")
public class Consumer {

    @Autowired
    private ConcurrentLinkedQueue<Record> queue;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Logger logger;

    private String sql = "insert into sb_api_response_summary(messageRowID,api,api_version,version,apiPublisher," +
            "consumerKey,userId,context,hostName,resourcePath,method,requestId,operatorId, msisdn,jsonBody,responseCode," +
            "senderAddress,time,year,month,day,serviceTime,purchaseCategoryCode,channel,onBehalfOf,description,taxAmount," +
            "transactionOperationStatus,referenceCode,senderName) " +
            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    @RequestMapping(value = "/consume/batchSize/{batchSize}/correlator/{correlator}", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity batchInsert(@PathVariable int batchSize, @PathVariable String correlator) {
        logger.info("Inserting batch [{}]", correlator);

        List<Record> records = new ArrayList<>();

        while (records.size() < batchSize) {
            Record record = queue.poll();
            if (record == null) {
                break;
            }
            records.add(record);
        }


        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Record record = records.get(i);
                preparedStatement.setString(1, record.getMessageRowId());
                preparedStatement.setString(2, record.getApi());
                preparedStatement.setString(3, record.getApiVersion());
                preparedStatement.setString(4, record.getVersion());
                preparedStatement.setString(5, record.getApiPublisher());
                preparedStatement.setString(6, record.getConsumerKey());
                preparedStatement.setString(7, record.getUserId());
                preparedStatement.setString(8, record.getContext());
                preparedStatement.setString(9, record.getHostName());
                preparedStatement.setString(10, record.getResourcePath());
                preparedStatement.setString(11, record.getMethod());
                preparedStatement.setString(12, record.getRequestId());
                preparedStatement.setString(13, record.getOperatorId());
                preparedStatement.setString(14, record.getMsisdn());
                preparedStatement.setString(15, record.getJsonBody());
                preparedStatement.setString(16, record.getResponseCode());
                preparedStatement.setString(17, record.getSenderAddress());
                preparedStatement.setString(18, record.getTime());
                preparedStatement.setInt(19, record.getYear());
                preparedStatement.setInt(20, record.getMonth());
                preparedStatement.setInt(21, record.getDay());
                preparedStatement.setInt(22, record.getServiceTime());
                preparedStatement.setString(23, record.getPurchaseCategoryCode());
                preparedStatement.setString(24, record.getChannel());
                preparedStatement.setString(25, record.getOnBehalfOf());
                preparedStatement.setString(26, record.getDescription());
                preparedStatement.setString(27, record.getTaxAmount());
                preparedStatement.setString(28, record.getTransactionOperationStatus());
                preparedStatement.setString(29, record.getReferenceCode());
                preparedStatement.setString(30, record.getSenderName());
            }

            @Override
            public int getBatchSize() {
                return records.size();
            }
        });

        return new ResponseEntity(HttpStatus.OK);
    }
}
