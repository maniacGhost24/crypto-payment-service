package com.cryptopayments.payment_core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cryptopayments.payment_core.dto.MerchantResponse;
import com.cryptopayments.payment_core.dto.UpdateWebhookRequest;
import com.cryptopayments.payment_core.entity.Merchant;
import com.cryptopayments.payment_core.repository.MerchantRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/merchant")
@RequiredArgsConstructor
@Validated
public class MerchantController {

    private final MerchantRepository merchantRepository;

    @PatchMapping("/webhook")
    public ResponseEntity<MerchantResponse> updateWebhook(
            Authentication authentication,
            @Valid @RequestBody UpdateWebhookRequest request) {

        Merchant merchant = merchantRepository
                .findByEmail(authentication.getName())
                .orElseThrow();

        merchant.setWebhookUrl(request.getWebhookUrl());

        merchantRepository.save(merchant);

        return ResponseEntity.ok(
                MerchantResponse.builder()
                        .email(merchant.getEmail())
                        .businessName(merchant.getBusinessName())
                        .webhookUrl(merchant.getWebhookUrl())
                        .build());
    }
}