package com.cryptopayments.payment_core.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WebhookPayload {

    private UUID paymentIntentId;

    private String status;

    private BigDecimal amount;

    private String currency;

    private String transactionHash;

    private Long blockNumber;
}