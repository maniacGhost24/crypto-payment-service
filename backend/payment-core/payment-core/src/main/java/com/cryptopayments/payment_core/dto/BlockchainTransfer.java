package com.cryptopayments.payment_core.dto;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class BlockchainTransfer {

    private String from;

    private String to;

    private String transactionHash;

    private BigInteger amount;

    private BigInteger blockNumber;
}