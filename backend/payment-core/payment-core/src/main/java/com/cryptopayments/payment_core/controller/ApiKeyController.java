package com.cryptopayments.payment_core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.cryptopayments.payment_core.dto.ApiKeySummaryResponse;
import com.cryptopayments.payment_core.dto.CreateApiKeyRequest;
import com.cryptopayments.payment_core.dto.CreateApiKeyResponse;
import com.cryptopayments.payment_core.entity.Merchant;
import com.cryptopayments.payment_core.repository.MerchantRepository;
import com.cryptopayments.payment_core.service.ApiKeyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "API Keys",
        description = "Manage merchant API keys")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/keys")
@RequiredArgsConstructor
public class ApiKeyController {
    private final ApiKeyService apiKeyService;
    private final MerchantRepository merchantRepository;
    @Operation(summary = "Generate API Key")
    @PostMapping
    public ResponseEntity<CreateApiKeyResponse> createKey(
            Authentication authentication,
            @Valid @RequestBody CreateApiKeyRequest request) {

        String email = authentication.getName();

        Merchant merchant = merchantRepository
                .findByEmail(email)
                .orElseThrow();

        CreateApiKeyResponse response = apiKeyService.createKey(
                merchant,
                request.getName());

        return ResponseEntity.ok(response);
    }
    @Operation(summary = "List API Keys")
    @GetMapping
    public ResponseEntity<List<ApiKeySummaryResponse>> getKeys(
            Authentication authentication) {

        String email = authentication.getName();

        Merchant merchant = merchantRepository
                .findByEmail(email)
                .orElseThrow();

        return ResponseEntity.ok(
                apiKeyService.getKeys(merchant));
    }
    @Operation(summary = "Revoke API Key")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> revokeKey(
        @PathVariable UUID id,
        Authentication authentication
    ){
        Merchant merchant = merchantRepository.findByEmail(authentication.getName()).orElseThrow();

        apiKeyService.revokeKey(id, merchant);

        return ResponseEntity.noContent().build();
    }
}
