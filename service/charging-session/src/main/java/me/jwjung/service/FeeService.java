package me.jwjung.service;

public interface FeeService {
	Integer calculateFee(String transactionId, Double chargedAmount);
}
