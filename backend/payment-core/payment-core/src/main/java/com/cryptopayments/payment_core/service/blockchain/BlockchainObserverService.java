package com.cryptopayments.payment_core.service.blockchain;

import java.math.BigInteger;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cryptopayments.payment_core.service.BlockchainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockchainObserverService {

    private final BlockchainCursorService cursorService;
    private final BlockchainService blockchainService;
    private final UsdcTransferService usdcTransferService;
    private final PaymentProcessingService paymentProcessingService;
    private final PaymentMatchingService paymentMatchingService;

    private static final int BLOCK_BATCH_SIZE = 9;

    @Scheduled(fixedDelay = 10000)
    public void observe() {

        BigInteger fromBlock = cursorService.getLastProcessedBlock();

        BigInteger latestBlock = blockchainService.getLatestBlockNumber();

        BigInteger maxToBlock = fromBlock.add(BigInteger.valueOf(BLOCK_BATCH_SIZE));

        BigInteger toBlock = maxToBlock.min(latestBlock);

        if (latestBlock.compareTo(fromBlock) <= 0) {
            return;
        }

        List<BlockchainTransfer> transfers = usdcTransferService.getTransfers(
                fromBlock.add(BigInteger.ONE),
                toBlock);

        for (BlockchainTransfer transfer : transfers) {

            paymentProcessingService.process(
                    transfer);

        }

        cursorService.update(latestBlock);
    }
}