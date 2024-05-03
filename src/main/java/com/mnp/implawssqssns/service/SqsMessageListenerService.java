package com.mnp.implawssqssns.service;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SqsMessageListenerService {

    private static final Logger LOG = LoggerFactory.getLogger(SqsMessageListenerService.class);
    private static final String queueName = "terraform-example-queue";

    @SqsListener(queueNames = queueName)
    public void listen(String message) {
        LOG.info("Queue {}, received a Message: {}", queueName, message);
    }
}
