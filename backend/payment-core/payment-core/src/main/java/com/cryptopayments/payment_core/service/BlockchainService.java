package com.cryptopayments.payment_core.service;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockchainService {

    private final Web3j web3j;

    public BigInteger getLatestBlockNumber() {

        try {

            return web3j
                    .ethBlockNumber()
                    .send()
                    .getBlockNumber();

        } catch (IOException e) {

            throw new RuntimeException("Failed to connect to Base RPC", e);

        }
    }
}