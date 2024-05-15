package com.mnp.implawssqssns.service.sns;

import io.awspring.cloud.sns.core.SnsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class SnsMessageSendingService {

    private static final Logger LOG = LoggerFactory.getLogger(SnsMessageSendingService.class);

    private final SnsTemplate snsTemplate;
    private final String snsTopic;

    public SnsMessageSendingService(SnsTemplate snsTemplate, @Value("${sns.topic}") final String snsTopic) {
        this.snsTemplate = snsTemplate;
        this.snsTopic = snsTopic;
    }

    public void sendMessage(String message) {
        try
        {
            snsTemplate.convertAndSend(snsTopic, message);

            LOG.info("SNS Message was sent to topic {}", snsTopic);
        } catch (MessagingException exception)
        {
            LOG.error("Error occurred when sending SNS Message to topic {}", snsTopic);


        }
    }
}
