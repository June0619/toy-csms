package me.jwjung.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;

@Getter
@RedisHash(value = "charging::session", timeToLive = 60 * 60 * 12)
public class Session {
	private final static int FEE_PER_KWH = 300;

	@Id
	private String transactionId;

	@Indexed
	private String memberUuid;

	private Double totalChargedAmount;
	private Double meterValueAmount;
	private Integer totalFee;
	private LocalDateTime lastUpdatedAt;

	public static Session create(String transactionId, String memberUuid) {
		Session session = new Session();
		session.transactionId = transactionId;
		session.memberUuid = memberUuid;
		session.totalChargedAmount = 0.0;
		session.meterValueAmount = 0.0;
		session.totalFee = 0;
		session.lastUpdatedAt = LocalDateTime.now();
		return session;
	}

	public void updateNewMeterValue(Double newMeterValue) {
		final double chargedAmount = Math.max(0, newMeterValue - this.meterValueAmount);
		this.totalChargedAmount += chargedAmount;
		this.meterValueAmount = newMeterValue;
		this.lastUpdatedAt = LocalDateTime.now();

		totalFee += BigDecimal.valueOf(chargedAmount * FEE_PER_KWH)
				.setScale(0, RoundingMode.UP)
				.intValueExact();
	}
}
