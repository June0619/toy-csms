package me.jwjung.common.event.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.jwjung.common.event.EventPayload;

@Data
@AllArgsConstructor
public class MeterValueChangedEventPayload implements EventPayload {
	private String transactionId;
	private Double chargedAmount;
}
