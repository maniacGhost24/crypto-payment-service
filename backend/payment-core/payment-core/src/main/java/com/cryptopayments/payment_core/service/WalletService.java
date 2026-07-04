package com.cryptopayments.payment_core.service;

import com.cryptopayments.payment_core.entity.WalletStatus;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.cryptopayments.payment_core.entity.Wallet;
import com.cryptopayments.payment_core.repository.WalletRepository;
import com.cryptopayments.payment_core.service.provider.WalletProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletProvider walletProvider;

    public Wallet reserveWallet() {

        Wallet wallet = walletRepository
                .findFirstByStatus(WalletStatus.AVAILABLE)
                .orElse(null);

        if (wallet == null) {
            wallet = walletProvider.createWallet();
            wallet = walletRepository.save(wallet);
        }

        wallet.setStatus(WalletStatus.RESERVED);
        wallet.setAssignedAt(Instant.now());

        return walletRepository.save(wallet);
    }
}