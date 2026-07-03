package com.cryptopayments.payment_core.service.blockchain;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cryptopayments.payment_core.entity.PaymentIntent;
import com.cryptopayments.payment_core.entity.Wallet;
import com.cryptopayments.payment_core.repository.PaymentIntentRepository;
import com.cryptopayments.payment_core.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMatchingService {

    private final WalletRepository walletRepository;
    private final PaymentIntentRepository paymentIntentRepository;

    public PaymentMatchResult match(
            BlockchainTransfer transfer) {

        return walletRepository
                .findByAddressIgnoreCase(transfer.getTo())
                .flatMap(paymentIntentRepository::findByWallet)
                .map(paymentIntent -> PaymentMatchResult.builder()
                        .matched(true)
                        .paymentIntent(paymentIntent)
                        .transfer(transfer)
                        .build())
                .orElse(
                        PaymentMatchResult.builder()
                                .matched(false)
                                .transfer(transfer)
                                .build());
    }
}