package com.cryptopayments.payment_core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "usdc")
@Getter
@Setter
public class UsdcProperties {

    private String contract;

}