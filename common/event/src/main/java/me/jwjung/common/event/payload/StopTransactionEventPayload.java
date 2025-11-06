package me.jwjung.common.event.payload;

import lombok.Data;
import me.jwjung.common.event.EventPayload;

@Data
public class StopTransactionEventPayload implements EventPayload {
	private String transactionId;
	private Double meterValueAmount;

	public StopTransactionEventPayload(final String transactionId, final Double meterValueAmount) {
		this.transactionId = transactionId;
		this.meterValueAmount = meterValueAmount;
	}

	public StopTransactionEventPayload() {
	}
}
