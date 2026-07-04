package com.cryptopayments.payment_core.service.blockchain;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentProcessingService {

    private final PaymentMatchingService paymentMatchingService;
    private final PaymentStateService paymentStateService;

    public void process(
            BlockchainTransfer transfer) {

        PaymentMatchResult result =
                paymentMatchingService.match(transfer);

        if (!result.isMatched()) {
            return;
        }

        System.out.println();
        System.out.println("==============================");
        System.out.println("PAYMENT MATCHED");
        System.out.println("Payment Intent : "
                + result.getPaymentIntent().getId());
        System.out.println("Wallet         : "
                + transfer.getTo());
        System.out.println("Amount         : "
                + transfer.getAmount());
        System.out.println("==============================");
        System.out.println();

        paymentStateService.markPaid(
                result.getPaymentIntent(),
                transfer);
    }
}