package com.cryptopayments.payment_core.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePaymentIntentRequest {

    @NotNull
    @DecimalMin("0.00000001")
    private BigDecimal amount;

    @NotBlank
    private String currency;
}