package com.cryptopayments.payment_core.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptopayments.payment_core.entity.ApiKey;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {

    Optional<ApiKey> findByKeyPrefix(String keyPrefix);
}