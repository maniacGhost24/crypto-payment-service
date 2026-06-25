package com.cryptopayments.payment_core.service.provider;

import java.security.SecureRandom;
import java.time.Instant;

import org.springframework.stereotype.Component;

import com.cryptopayments.payment_core.entity.Network;
import com.cryptopayments.payment_core.entity.Wallet;
import com.cryptopayments.payment_core.entity.WalletStatus;

@Component
public class DummyWalletProvider implements WalletProvider {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public Wallet createWallet() {

        return Wallet.builder()
                .address(generateAddress())
                .network(Network.BASE)
                .status(WalletStatus.AVAILABLE)
                .createdAt(Instant.now())
                .build();
    }

    private String generateAddress() {

        byte[] bytes = new byte[20];

        RANDOM.nextBytes(bytes);

        StringBuilder address = new StringBuilder("0x");

        for (byte b : bytes) {
            address.append(String.format("%02x", b));
        }

        return address.toString();
    }
}