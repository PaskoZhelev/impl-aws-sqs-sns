package com.mnp.implawssqssns.controller;

import com.mnp.implawssqssns.service.SqsMessageSendingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class MessagingSendingController {

    private final SqsMessageSendingService sqsMessageSendingService;

    public MessagingSendingController(final SqsMessageSendingService sqsMessageSendingService) {
        this.sqsMessageSendingService = sqsMessageSendingService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody String message) {
        sqsMessageSendingService.sendMessage("terraform-example-queue", message);

        return ResponseEntity.ok("Message was sent successfully");
    }
}
