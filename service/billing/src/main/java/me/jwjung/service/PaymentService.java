package me.jwjung.service;

import java.util.random.RandomGenerator;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

	public void payment(String memberUuid, int amount) {
		String billingKey = findByMemberUuid(memberUuid);
		approveFromPgServer(billingKey, amount);
	}

	public String findByMemberUuid(String memberUuid) {
		return "billing-key-" + memberUuid;
	}

	private void approveFromPgServer(String billingKey, int amount) {
		randomSleep(500, 2000);

		if (amount > 10000) {
			throw new NotEnoughBalanceException();
		}

	}

	private void randomSleep(int minMillis, int maxMillis) {
		try {
			Thread.sleep(RandomGenerator.getDefault().nextInt(minMillis, maxMillis));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
