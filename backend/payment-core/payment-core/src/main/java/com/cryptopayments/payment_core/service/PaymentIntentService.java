package com.cryptopayments.payment_core.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cryptopayments.payment_core.dto.CreatePaymentIntentRequest;
import com.cryptopayments.payment_core.dto.PaymentIntentResponse;
import com.cryptopayments.payment_core.entity.Merchant;
import com.cryptopayments.payment_core.entity.PaymentIntent;
import com.cryptopayments.payment_core.entity.PaymentIntentStatus;
import com.cryptopayments.payment_core.repository.PaymentIntentRepository;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentIntentService {

        private final PaymentIntentRepository paymentIntentRepository;

        public PaymentIntentResponse createPaymentIntent(
                        Merchant merchant,
                        CreatePaymentIntentRequest request) {

                PaymentIntent paymentIntent = PaymentIntent.builder()
                                .merchant(merchant)
                                .amount(request.getAmount())
                                .currency(request.getCurrency())
                                .status(PaymentIntentStatus.PENDING)
                                .createdAt(Instant.now())
                                .build();

                paymentIntentRepository.save(paymentIntent);

                return PaymentIntentResponse.builder()
                                .id(paymentIntent.getId())
                                .amount(paymentIntent.getAmount())
                                .currency(paymentIntent.getCurrency())
                                .status(paymentIntent.getStatus())
                                .createdAt(paymentIntent.getCreatedAt())
                                .build();
        }

        public List<PaymentIntentResponse> getPaymentIntents(
                        Merchant merchant) {

                return paymentIntentRepository
                                .findByMerchant(merchant)
                                .stream()
                                .map(paymentIntent -> PaymentIntentResponse.builder()
                                                .id(paymentIntent.getId())
                                                .amount(paymentIntent.getAmount())
                                                .currency(paymentIntent.getCurrency())
                                                .status(paymentIntent.getStatus())
                                                .createdAt(paymentIntent.getCreatedAt())
                                                .build())
                                .toList();
        }

        public PaymentIntentResponse getPaymentIntent(
                        UUID id,
                        Merchant merchant) {

                PaymentIntent paymentIntent = paymentIntentRepository
                                .findByIdAndMerchant(id, merchant)
                                .orElseThrow();

                return PaymentIntentResponse.builder()
                                .id(paymentIntent.getId())
                                .amount(paymentIntent.getAmount())
                                .currency(paymentIntent.getCurrency())
                                .status(paymentIntent.getStatus())
                                .createdAt(paymentIntent.getCreatedAt())
                                .build();
        }
}