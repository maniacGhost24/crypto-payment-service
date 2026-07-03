package com.cryptopayments.payment_core.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptopayments.payment_core.entity.Wallet;
import com.cryptopayments.payment_core.entity.WalletStatus;

public interface WalletRepository
        extends JpaRepository<Wallet, UUID> {

    Optional<Wallet> findFirstByStatus(
            WalletStatus status);

    Optional<Wallet> findByAddressIgnoreCase(
            String address);

}