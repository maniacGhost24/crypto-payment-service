package com.cryptopayments.payment_core.service.blockchain;

import java.math.BigInteger;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cryptopayments.payment_core.entity.PaymentIntent;
import com.cryptopayments.payment_core.repository.PaymentIntentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentReplayService {

    private final PaymentIntentRepository paymentIntentRepository;

    private final PaymentProcessingService paymentProcessingService;

    public void replay(UUID paymentIntentId) {

        PaymentIntent paymentIntent =
                paymentIntentRepository.findById(paymentIntentId)
                        .orElseThrow();

        BlockchainTransfer transfer =
                BlockchainTransfer.builder()
                        .from("0xTEST")
                        .to(paymentIntent.getWallet().getAddress())
                        .transactionHash(
                                "0xTEST"
                                        + System.currentTimeMillis())
                        .amount(paymentIntent.getAmount()
                                .movePointRight(6)
                                .toBigInteger())
                        .blockNumber(BigInteger.valueOf(99999999))
                        .build();

        paymentProcessingService.process(transfer);
    }
}