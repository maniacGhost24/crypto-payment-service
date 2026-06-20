package com.cryptopayments.payment_core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateApiKeyRequest {
    
    @NotBlank
    private String name;
}
