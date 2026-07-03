package com.cryptopayments.payment_core.controller;

import java.math.BigInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptopayments.payment_core.service.BlockchainService;
import com.cryptopayments.payment_core.service.blockchain.BlockchainCursorService;
import com.cryptopayments.payment_core.service.blockchain.UsdcTransferService;
import com.cryptopayments.payment_core.service.blockchain.BlockchainTransfer;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BlockchainController {

    private final BlockchainService blockchainService;
    private final BlockchainCursorService blockchainCursorService;

    private final UsdcTransferService usdcTransferService;

    @GetMapping("/api/blockchain/latest-block")
    public BigInteger latestBlock() {

        return blockchainService.getLatestBlockNumber();

    }

    @GetMapping("/api/blockchain/cursor")
    public BigInteger cursor() {

        return blockchainCursorService.getLastProcessedBlock();
    }

    @GetMapping("/api/blockchain/test-transfers")
    public String testTransfers() {

        BigInteger latestBlock = blockchainService.getLatestBlockNumber();

        List<BlockchainTransfer> transfers = usdcTransferService.getTransfers(
                latestBlock.subtract(BigInteger.valueOf(9)),
                latestBlock);

        transfers.forEach(System.out::println);

        return "Found " + transfers.size() + " transfers";
    }
}