package com.mnp.implawssqssns.controller;

import com.mnp.implawssqssns.service.sns.SnsMessageSendingService;
import com.mnp.implawssqssns.service.sqs.SqsMessageSendingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class MessageSendingController {

    private final SqsMessageSendingService sqsMessageSendingService;
    private final SnsMessageSendingService snsMessageSendingService;

    public MessageSendingController(final SqsMessageSendingService sqsMessageSendingService, final SnsMessageSendingService snsMessageSendingService) {
        this.sqsMessageSendingService = sqsMessageSendingService;
        this.snsMessageSendingService = snsMessageSendingService;
    }

    @PostMapping("/sqs/send")
    public ResponseEntity<String> sendSqs(@RequestBody String message) {
        sqsMessageSendingService.sendMessage(message);

        return ResponseEntity.ok("Message was sent successfully");
    }

    @PostMapping("/sns/send")
    public ResponseEntity<String> sendSns(@RequestBody String message) {

        snsMessageSendingService.sendMessage(message);

        return ResponseEntity.ok("Message was sent successfully");
    }
}
