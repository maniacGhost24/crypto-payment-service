package com.cryptopayments.payment_core.repository;

import java.util.UUID;
import java.util.Optional;

import com.cryptopayments.payment_core.entity.Wallet;
import com.cryptopayments.payment_core.entity.WalletStatus;


import org.springframework.data.jpa.repository.JpaRepository;



public interface WalletRepository
        extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findFirstByStatus(
            WalletStatus status);
}