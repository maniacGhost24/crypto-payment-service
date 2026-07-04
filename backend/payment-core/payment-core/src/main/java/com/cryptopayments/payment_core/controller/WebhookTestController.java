package com.cryptopayments.payment_core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class WebhookTestController {

    @PostMapping("/webhook")
    public ResponseEntity<Void> receive(
            @RequestBody String body) {

        log.info("==============================");
        log.info("WEBHOOK RECEIVED");
        log.info(body);
        log.info("==============================");

        return ResponseEntity.ok().build();
    }
}