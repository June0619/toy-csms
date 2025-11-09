package me.jwjung.domain;

public record BillingRequest(String transactionId, String memberUuid, Integer billingFee) {
}
