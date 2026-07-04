package com.cryptopayments.payment_core.controller;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptopayments.payment_core.dto.LoginRequest;
import com.cryptopayments.payment_core.dto.SignupRequest;
import com.cryptopayments.payment_core.entity.Merchant;
import com.cryptopayments.payment_core.repository.MerchantRepository;
import com.cryptopayments.payment_core.service.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(
        name = "Authentication",
        description = "Merchant authentication APIs")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Operation(summary = "Register Merchant")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @Valid @RequestBody SignupRequest request
    ) {

        if (merchantRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Email already exists");
        }

        Merchant merchant = Merchant.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .businessName(request.getBusinessName())
                .createdAt(Instant.now())
                .build();

        merchantRepository.save(merchant);

        return ResponseEntity.ok("Merchant registered successfully");
    }
    @Operation(summary = "Merchant Login")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request
    ) {

        Merchant merchant = merchantRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (merchant == null) {
            return ResponseEntity.badRequest()
                    .body("Invalid credentials");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                merchant.getPasswordHash()
        )) {

            return ResponseEntity.badRequest()
                    .body("Invalid credentials");
        }

        String token = jwtService.generateToken(merchant.getEmail());

        return ResponseEntity.ok(token);
    }
}