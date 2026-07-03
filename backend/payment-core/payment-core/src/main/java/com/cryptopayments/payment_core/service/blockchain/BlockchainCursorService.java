package com.cryptopayments.payment_core.service.blockchain;

import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryptopayments.payment_core.entity.BlockchainCursor;
import com.cryptopayments.payment_core.repository.BlockchainCursorRepository;
import com.cryptopayments.payment_core.service.BlockchainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockchainCursorService {

    private static final Long CURSOR_ID = 1L;

    private final BlockchainCursorRepository cursorRepository;
    private final BlockchainService blockchainService;

    @Transactional
    public BigInteger getLastProcessedBlock() {

        return cursorRepository.findById(CURSOR_ID)
                .map(BlockchainCursor::getLastProcessedBlock)
                .orElseGet(() -> {

                    BigInteger latestBlock = blockchainService.getLatestBlockNumber();

                    BlockchainCursor cursor = BlockchainCursor.builder()
                            .id(CURSOR_ID)
                            .lastProcessedBlock(latestBlock)
                            .build();

                    cursorRepository.save(cursor);

                    return latestBlock;
                });
    }

    @Transactional
    public void update(BigInteger latestProcessedBlock) {

        BlockchainCursor cursor = cursorRepository.findById(CURSOR_ID)
                .orElseThrow();

        cursor.setLastProcessedBlock(latestProcessedBlock);

        cursorRepository.save(cursor);
    }
}