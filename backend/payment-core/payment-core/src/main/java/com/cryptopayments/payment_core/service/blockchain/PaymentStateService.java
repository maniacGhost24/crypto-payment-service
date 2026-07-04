package com.cryptopayments.payment_core.service.blockchain;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryptopayments.payment_core.entity.PaymentIntent;
import com.cryptopayments.payment_core.entity.PaymentIntentStatus;
import com.cryptopayments.payment_core.repository.PaymentIntentRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class PaymentStateService {

        private final PaymentIntentRepository paymentIntentRepository;

        public void markPaid(
                        PaymentIntent paymentIntent,
                        BlockchainTransfer transfer) {

                if (paymentIntent.getStatus() == PaymentIntentStatus.PAID) {
                        return;
                }

                paymentIntent.setStatus(PaymentIntentStatus.PAID);
                paymentIntent.setPaidAt(Instant.now());

                System.out.println();
                System.out.println("==============================");
                System.out.println("PAYMENT UPDATED");
                System.out.println("Status : PAID");
                System.out.println("Tx Hash: " + transfer.getTransactionHash());
                System.out.println("==============================");
                System.out.println();

                paymentIntent.setTransactionHash(
                                transfer.getTransactionHash());

                paymentIntent.setBlockNumber(
                                transfer.getBlockNumber().longValue());

                paymentIntentRepository.save(paymentIntent);

                System.out.println(
                                "Payment marked as PAID: "
                                                + paymentIntent.getId());
        }
}