package me.jwjung.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleFeeService implements FeeService {
	private static final double UNIT_PRICE = 300.0; // 1kWh당 요금

	@Override
	public Integer calculateFee(String transactionId, Double chargedAmount) {
		if (chargedAmount == null || chargedAmount < 0) {
			return 0;
		}

		double fee = chargedAmount * UNIT_PRICE;
		log.debug("[SimpleFeeService.calculateFee] {} → {}kWh * {}원 = {}원",
				transactionId, chargedAmount, UNIT_PRICE, fee);

		return (int) Math.round(fee);
	}
}
