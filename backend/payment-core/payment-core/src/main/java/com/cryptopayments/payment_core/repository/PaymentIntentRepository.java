package com.cryptopayments.payment_core.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptopayments.payment_core.entity.Merchant;
import com.cryptopayments.payment_core.entity.PaymentIntent;
import com.cryptopayments.payment_core.entity.Wallet;

public interface PaymentIntentRepository
                extends JpaRepository<PaymentIntent, UUID> {

        List<PaymentIntent> findByMerchant(
                        Merchant merchant);

        Optional<PaymentIntent> findByIdAndMerchant(
                        UUID id,
                        Merchant merchant);

        Optional<PaymentIntent> findByWallet(
                        Wallet wallet);

        Optional<PaymentIntent> findById(UUID id);
}