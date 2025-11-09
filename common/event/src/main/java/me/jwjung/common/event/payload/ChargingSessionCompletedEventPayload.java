package me.jwjung.common.event.payload;

import java.time.LocalDateTime;

import lombok.Data;
import me.jwjung.common.event.EventPayload;

@Data
public class ChargingSessionCompletedEventPayload implements EventPayload {
	private String transactionId;
	private String memberUuid;
	private Integer billingFee;
	private LocalDateTime startedAt;
	private LocalDateTime completedAt;
	private Double totalChargedAmount;

	public ChargingSessionCompletedEventPayload() {
	}

	public ChargingSessionCompletedEventPayload(
			final String transactionId,
			final String memberUuid,
			final Integer billingFee,
			final LocalDateTime startedAt,
			final LocalDateTime completedAt,
			final Double totalChargedAmount
	) {
		this.transactionId = transactionId;
		this.memberUuid = memberUuid;
		this.billingFee = billingFee;
		this.startedAt = startedAt;
		this.completedAt = completedAt;
		this.totalChargedAmount = totalChargedAmount;
	}
}
