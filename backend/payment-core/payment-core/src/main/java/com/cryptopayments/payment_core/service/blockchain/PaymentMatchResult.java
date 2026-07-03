package com.cryptopayments.payment_core.service.blockchain;

import com.cryptopayments.payment_core.entity.PaymentIntent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentMatchResult {

    private BlockchainTransfer transfer;

    private PaymentIntent paymentIntent;

    private boolean matched;
}