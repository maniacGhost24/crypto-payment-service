package com.cryptopayments.payment_core.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(Authentication authentication) {

        return "Authenticated merchant: "
                + authentication.getName();
    }

    // @GetMapping("/api/api-key-test")
    // public String apiKeyTest(
    //     Authentication authentication
    // ){
    //     return authentication.getName();
    // }
}