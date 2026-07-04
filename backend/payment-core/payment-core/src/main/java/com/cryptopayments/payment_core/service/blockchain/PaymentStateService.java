package com.cryptopayments.payment_core.service.blockchain;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryptopayments.payment_core.entity.PaymentIntent;
import com.cryptopayments.payment_core.entity.PaymentIntentStatus;
import com.cryptopayments.payment_core.repository.PaymentIntentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentStateService {

        private final PaymentIntentRepository paymentIntentRepository;

        @Transactional
        public boolean markPaid(
                        PaymentIntent paymentIntent,
                        BlockchainTransfer transfer) {

                if (paymentIntent.getStatus() == PaymentIntentStatus.PAID) {
                        return false;
                }

                paymentIntent.setStatus(PaymentIntentStatus.PAID);
                paymentIntent.setPaidAt(Instant.now());
                paymentIntent.setTransactionHash(
                                transfer.getTransactionHash());

                paymentIntent.setBlockNumber(
                                transfer.getBlockNumber().longValue());

                paymentIntentRepository.save(paymentIntent);

                log.info("Payment {} marked as PAID",
                                paymentIntent.getId());

                return true;
        }
}