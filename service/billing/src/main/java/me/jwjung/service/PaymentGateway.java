package me.jwjung.service;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.jwjung.domain.BillingRequest;

@Slf4j
@Component
public class PaymentGateway {

	public void requestPayment(BillingRequest billingRequest) {
		if (billingRequest.memberUuid().contains("fail")) {
			throw new NotEnoughBalanceException();
		}

		log.info("[PaymentGateway.requestPayment] memberUuid={}, amount={}",
				billingRequest.memberUuid(), billingRequest.billingFee());
	}
}
