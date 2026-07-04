package com.cryptopayments.payment_core.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cryptopayments.payment_core.dto.WebhookPayload;
import com.cryptopayments.payment_core.entity.PaymentIntent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhookService {

    private final RestTemplate restTemplate;

    public void send(PaymentIntent paymentIntent) {

        String webhookUrl =
                paymentIntent.getMerchant().getWebhookUrl();

        if (webhookUrl == null || webhookUrl.isBlank()) {

            log.info("Merchant has no webhook configured.");

            return;
        }

        WebhookPayload payload =
                WebhookPayload.builder()
                        .paymentIntentId(paymentIntent.getId())
                        .status(paymentIntent.getStatus().name())
                        .amount(paymentIntent.getAmount())
                        .currency(paymentIntent.getCurrency())
                        .transactionHash(paymentIntent.getTransactionHash())
                        .blockNumber(paymentIntent.getBlockNumber())
                        .build();

        try {

            restTemplate.postForEntity(
                    webhookUrl,
                    payload,
                    Void.class);

            log.info("Webhook delivered successfully.");

        } catch (Exception ex) {

            log.error("Webhook delivery failed.", ex);

        }

    }
}