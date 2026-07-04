package com.cryptopayments.payment_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PaymentCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentCoreApplication.class, args);
	}

}
