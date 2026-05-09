package com.cryptopayments.payment_core.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void shouldGenerateAndExtractEmail() {

        String email = "test@example.com";

        String token = jwtService.generateToken(email);

        String extracted =
                jwtService.extractEmail(token);

        assertEquals(email, extracted);
    }
}