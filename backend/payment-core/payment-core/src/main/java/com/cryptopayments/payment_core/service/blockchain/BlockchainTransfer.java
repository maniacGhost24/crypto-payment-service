package com.cryptopayments.payment_core.service.blockchain;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlockchainTransfer {

    private String from;

    private String to;

    private String transactionHash;

    private BigInteger amount;

    private BigInteger blockNumber;
}