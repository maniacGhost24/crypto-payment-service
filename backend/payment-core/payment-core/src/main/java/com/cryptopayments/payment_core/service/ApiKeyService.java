package com.cryptopayments.payment_core.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cryptopayments.payment_core.dto.ApiKeySummaryResponse;
import com.cryptopayments.payment_core.dto.CreateApiKeyResponse;
import com.cryptopayments.payment_core.entity.ApiKey;
import com.cryptopayments.payment_core.entity.Merchant;
import com.cryptopayments.payment_core.repository.ApiKeyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;

    private static final SecureRandom RANDOM = new SecureRandom();

    public CreateApiKeyResponse createKey(
            Merchant merchant,
            String name) {

        byte[] randomBytes = new byte[32];
        RANDOM.nextBytes(randomBytes);

        String token = HexFormat.of().formatHex(randomBytes);

        String rawKey = "cp_live_" + token;

        String prefix = rawKey.substring(0, 15);

        String hashedKey = hashApiKey(rawKey);

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .name(name)
                .keyPrefix(prefix)
                .hashedKey(hashedKey)
                .revoked(false)
                .createdAt(Instant.now())
                .build();

        apiKeyRepository.save(apiKey);

        return CreateApiKeyResponse.builder()
                .apiKey(rawKey)
                .prefix(prefix)
                .build();
    }

    public String hashApiKey(String value) {

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    value.getBytes(
                            StandardCharsets.UTF_8));

            return HexFormat.of()
                    .formatHex(hash);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<ApiKeySummaryResponse> getKeys(
            Merchant merchant) {

        return apiKeyRepository
                .findByMerchant(merchant)
                .stream()
                .map(key -> ApiKeySummaryResponse.builder()
                        .id(key.getId())
                        .name(key.getName())
                        .prefix(key.getKeyPrefix())
                        .revoked(key.isRevoked())
                        .createdAt(key.getCreatedAt())
                        .lastUsedAt(key.getLastUsedAt())
                        .build())
                .toList();
    }

    public void revokeKey(
            UUID keyId,
            Merchant merchant) {
        ApiKey apiKey = apiKeyRepository.findByIdAndMerchant(keyId, merchant).orElseThrow();

        apiKey.setRevoked(true);
        apiKeyRepository.save(apiKey);
    }

    public Merchant authenticateApiKey(String rawKey) {

        String hashedKey = hashApiKey(rawKey);

        ApiKey apiKey = apiKeyRepository
                .findByHashedKey(hashedKey)
                .orElse(null);

        if (apiKey == null) {
            return null;
        }

        if (apiKey.isRevoked()) {
            return null;
        }

        apiKey.setLastUsedAt(Instant.now());
        apiKeyRepository.save(apiKey);

        return apiKey.getMerchant();
    }
}
