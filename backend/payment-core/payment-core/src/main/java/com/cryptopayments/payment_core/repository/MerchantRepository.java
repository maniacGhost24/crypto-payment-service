package com.cryptopayments.payment_core.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptopayments.payment_core.entity.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant,UUID>{
    
    Optional<Merchant> findByEmail(String email);
}
