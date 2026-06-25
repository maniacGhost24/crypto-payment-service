package com.cryptopayments.payment_core.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cryptopayments.payment_core.dto.CreatePaymentIntentRequest;
import com.cryptopayments.payment_core.dto.PaymentIntentResponse;
import com.cryptopayments.payment_core.entity.Merchant;
import com.cryptopayments.payment_core.entity.PaymentIntent;
import com.cryptopayments.payment_core.entity.PaymentIntentStatus;
import com.cryptopayments.payment_core.entity.Wallet;
import com.cryptopayments.payment_core.repository.PaymentIntentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentIntentService {

        private final PaymentIntentRepository paymentIntentRepository;
        private final WalletService walletService;

        public PaymentIntentResponse createPaymentIntent(
                        Merchant merchant,
                        CreatePaymentIntentRequest request) {

                Wallet wallet = walletService.reserveWallet();

                PaymentIntent paymentIntent = PaymentIntent.builder()
                                .merchant(merchant)
                                .wallet(wallet)
                                .amount(request.getAmount())
                                .currency(request.getCurrency())
                                .status(PaymentIntentStatus.PENDING)
                                .createdAt(Instant.now())
                                .build();

                paymentIntentRepository.save(paymentIntent);

                return toResponse(paymentIntent);
        }

        public List<PaymentIntentResponse> getPaymentIntents(
                        Merchant merchant) {

                return paymentIntentRepository
                                .findByMerchant(merchant)
                                .stream()
                                .map(this::toResponse)
                                .toList();
        }

        public PaymentIntentResponse getPaymentIntent(
                        UUID id,
                        Merchant merchant) {

                PaymentIntent paymentIntent = paymentIntentRepository
                                .findByIdAndMerchant(id, merchant)
                                .orElseThrow();

                return toResponse(paymentIntent);
        }

        private PaymentIntentResponse toResponse(
                        PaymentIntent paymentIntent) {

                Wallet wallet = paymentIntent.getWallet();

                return PaymentIntentResponse.builder()
                                .id(paymentIntent.getId())
                                .amount(paymentIntent.getAmount())
                                .currency(paymentIntent.getCurrency())
                                .status(paymentIntent.getStatus())
                                .createdAt(paymentIntent.getCreatedAt())
                                .walletAddress(wallet != null ? wallet.getAddress() : null)
                                .network(wallet != null ? wallet.getNetwork() : null)
                                .paymentUri(wallet != null ? buildPaymentUri(paymentIntent) : null)
                                .build();
        }

        private String buildPaymentUri(
                        PaymentIntent paymentIntent) {

                Wallet wallet = paymentIntent.getWallet();

                return "ethereum:"
                                + wallet.getAddress()
                                + "?value="
                                + paymentIntent.getAmount()
                                + "&token="
                                + paymentIntent.getCurrency();
        }

        public String getPaymentUri(
                        UUID id,
                        Merchant merchant) {

                PaymentIntent paymentIntent = paymentIntentRepository
                                .findByIdAndMerchant(id, merchant)
                                .orElseThrow();

                return buildPaymentUri(paymentIntent);
        }
}