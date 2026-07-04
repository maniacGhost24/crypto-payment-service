package com.cryptopayments.payment_core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWebhookRequest {

    @NotBlank
    private String webhookUrl;

}