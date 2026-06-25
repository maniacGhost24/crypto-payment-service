package com.cryptopayments.payment_core.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.cryptopayments.payment_core.entity.PaymentIntentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentIntentResponse {

    private UUID id;
    private BigDecimal amount;
    private String currency;
    private PaymentIntentStatus status;
    private Instant createdAt;
}