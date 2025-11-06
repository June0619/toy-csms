package me.jwjung.common.event.payload;

import lombok.Data;
import me.jwjung.common.event.EventPayload;

@Data
public class MeterValueChangedEventPayload implements EventPayload {
	private String transactionId;
	private Double meterValueAmount;

	public MeterValueChangedEventPayload() {
	}

	public MeterValueChangedEventPayload(final String transactionId, final Double meterValueAmount) {
		this.transactionId = transactionId;
		this.meterValueAmount = meterValueAmount;
	}
}
