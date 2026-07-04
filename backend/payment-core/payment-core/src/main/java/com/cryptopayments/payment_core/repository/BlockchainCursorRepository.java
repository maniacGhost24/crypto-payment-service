package com.cryptopayments.payment_core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptopayments.payment_core.entity.BlockchainCursor;

public interface BlockchainCursorRepository
        extends JpaRepository<BlockchainCursor, Long> {

    Optional<BlockchainCursor> findById(Long id);

}