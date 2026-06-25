package com.cryptopayments.payment_core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.cryptopayments.payment_core.dto.CreatePaymentIntentRequest;
import com.cryptopayments.payment_core.dto.PaymentIntentResponse;
import com.cryptopayments.payment_core.entity.Merchant;
import com.cryptopayments.payment_core.repository.MerchantRepository;
// import com.cryptopayments.payment_core.repository.PaymentIntentRepository;
import com.cryptopayments.payment_core.service.PaymentIntentService;
import com.cryptopayments.payment_core.service.QRCodeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment-intents")
@RequiredArgsConstructor
public class PaymentIntentController {

        // private final PaymentIntentRepository paymentIntentRepository;
        private final MerchantRepository merchantRepository;
        private final PaymentIntentService paymentIntentService;

        private final QRCodeService qrCodeService;

        // PaymentIntentController(PaymentIntentRepository paymentIntentRepository) {
        // this.paymentIntentRepository = paymentIntentRepository;
        // }

        @PostMapping
        public ResponseEntity<PaymentIntentResponse> create(
                        Authentication authentication,
                        @Valid @RequestBody CreatePaymentIntentRequest request) {

                Merchant merchant = merchantRepository
                                .findByEmail(authentication.getName())
                                .orElseThrow();

                return ResponseEntity.ok(
                                paymentIntentService.createPaymentIntent(
                                                merchant,
                                                request));
        }

        @GetMapping
        public ResponseEntity<List<PaymentIntentResponse>> getPaymentIntents(
                        Authentication authentication) {

                Merchant merchant = merchantRepository
                                .findByEmail(authentication.getName())
                                .orElseThrow();

                return ResponseEntity.ok(
                                paymentIntentService.getPaymentIntents(merchant));
        }

        @GetMapping("/{id}")
        public ResponseEntity<PaymentIntentResponse> getPaymentIntent(
                        @PathVariable UUID id,
                        Authentication authentication) {

                Merchant merchant = merchantRepository
                                .findByEmail(authentication.getName())
                                .orElseThrow();

                return ResponseEntity.ok(
                                paymentIntentService.getPaymentIntent(id, merchant));
        }

        @GetMapping("/{id}/qr")
        public ResponseEntity<byte[]> getQrCode(
                        @PathVariable UUID id,
                        @RequestParam(defaultValue = "250") int size,
                        Authentication authentication) {

                Merchant merchant = merchantRepository
                                .findByEmail(authentication.getName())
                                .orElseThrow();

                String paymentUri = paymentIntentService.getPaymentUri(
                                id,
                                merchant);

                byte[] qr = qrCodeService.generateQrCode(
                                paymentUri,
                                size);

                return ResponseEntity.ok()
                                .header("Content-Type", "image/png")
                                .body(qr);
        }
}