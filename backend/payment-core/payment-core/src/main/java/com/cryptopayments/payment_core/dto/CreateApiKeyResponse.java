package com.cryptopayments.payment_core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateApiKeyResponse {
    
    private String apiKey;
    private String prefix;
}
