package com.mnp.implawssqssns.service;

import io.awspring.cloud.sqs.operations.MessagingOperationFailedException;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SqsMessageSendingService {

    private static final Logger LOG = LoggerFactory.getLogger(SqsMessageSendingService.class);

    private final SqsTemplate sqsTemplate;

    public SqsMessageSendingService(final SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendMessage(String queueName, String message) {

        try
        {
            final SendResult<String> sendResult = sqsTemplate.send(queueName, message);

            LOG.info("SQS Message with id {} was sent to queue {}", sendResult.messageId(), queueName);
        } catch (MessagingOperationFailedException exception)
        {
            LOG.error("Error occurred when sending SQS Message to queue {}, messages: {}", queueName, exception.getFailedMessages().stream().map(Object::toString).collect(Collectors.joining(",")));


        }

    }
}
