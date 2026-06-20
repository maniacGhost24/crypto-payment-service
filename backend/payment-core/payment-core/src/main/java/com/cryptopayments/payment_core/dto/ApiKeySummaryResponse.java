package com.cryptopayments.payment_core.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiKeySummaryResponse {
    
    private UUID id;
    private String name;
    private String prefix;
    private boolean revoked;
    private Instant createdAt;
    private Instant lastUsedAt;
}
