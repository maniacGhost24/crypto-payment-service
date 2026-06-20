package com.cryptopayments.payment_core.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptopayments.payment_core.entity.Merchant;
import com.cryptopayments.payment_core.entity.PaymentIntent;

public interface PaymentIntentRepository
        extends JpaRepository<PaymentIntent, UUID> {

    List<PaymentIntent> findByMerchant(
            Merchant merchant
    );
}