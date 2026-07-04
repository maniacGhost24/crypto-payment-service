package com.cryptopayments.payment_core.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptopayments.payment_core.dto.ReplayPaymentResponse;
import com.cryptopayments.payment_core.service.blockchain.PaymentReplayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DevController {

    private final PaymentReplayService paymentReplayService;

    @PostMapping("/api/dev/replay-payment/{paymentIntentId}")
    public ReplayPaymentResponse replay(
            @PathVariable UUID paymentIntentId) {

        paymentReplayService.replay(paymentIntentId);

        return ReplayPaymentResponse.builder()
                .paymentIntentId(paymentIntentId.toString())
                .status("PAID")
                .message("Replay successful")
                .build();
    }
}