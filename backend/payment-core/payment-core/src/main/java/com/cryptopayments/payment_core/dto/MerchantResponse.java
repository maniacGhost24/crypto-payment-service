package com.cryptopayments.payment_core.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MerchantResponse {

    private String email;

    private String businessName;

    private String webhookUrl;

}
