package com.cryptopayments.payment_core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptopayments.payment_core.entity.ApiKey;
import com.cryptopayments.payment_core.entity.Merchant;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {

    Optional<ApiKey> findByKeyPrefix(String keyPrefix);

    Optional<ApiKey> findByHashedKey(String hashedKey);

    List<ApiKey> findByMerchant(Merchant merchant);

    Optional<ApiKey> findByIdAndMerchant(
        UUID id,
        Merchant merchant
    );
}