package com.cryptopayments.payment_core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigInteger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockchainCursor {

    @Id
    private Long id;

    private BigInteger lastProcessedBlock;
}