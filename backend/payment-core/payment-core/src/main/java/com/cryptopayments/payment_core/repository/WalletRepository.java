package com.cryptopayments.payment_core.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptopayments.payment_core.entity.Wallet;

public interface WalletRepository
        extends JpaRepository<Wallet, UUID> {
}