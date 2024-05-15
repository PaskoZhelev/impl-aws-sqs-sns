package com.mnp.implawssqssns.service.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsMessageListenerService {

    private static final Logger LOG = LoggerFactory.getLogger(SqsMessageListenerService.class);
    private final String queueName;

    public SqsMessageListenerService(@Value("${queue.name}") final String queueName) {
        this.queueName = queueName;
    }

    @SqsListener(queueNames = "${queue.name}")
    public void listen(String message) {
        LOG.info("Queue {}, received a Message: {}", queueName, message);
    }
}
