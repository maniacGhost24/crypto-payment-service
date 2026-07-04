package com.cryptopayments.payment_core.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplayPaymentResponse {

    private String paymentIntentId;
    private String status;
    private String message;
}