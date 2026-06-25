package com.cryptopayments.payment_core.service;

import com.cryptopayments.payment_core.entity.WalletStatus;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.cryptopayments.payment_core.entity.Wallet;
import com.cryptopayments.payment_core.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet reserveWallet() {

        Wallet wallet = walletRepository
                .findFirstByStatus(WalletStatus.AVAILABLE)
                .orElseThrow();

        wallet.setStatus(WalletStatus.RESERVED);
        wallet.setAssignedAt(Instant.now());

        return walletRepository.save(wallet);
    }

}
