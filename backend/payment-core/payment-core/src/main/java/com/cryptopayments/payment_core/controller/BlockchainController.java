package com.cryptopayments.payment_core.controller;

import java.math.BigInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptopayments.payment_core.service.BlockchainService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BlockchainController {

    private final BlockchainService blockchainService;

    @GetMapping("/api/blockchain/latest-block")
    public BigInteger latestBlock() {

        return blockchainService.getLatestBlockNumber();

    }
}