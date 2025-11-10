package me.jwjung.common.event.payload;

import lombok.Data;
import me.jwjung.common.event.EventPayload;

@Data
public class PaymentFailedEventPayload implements EventPayload {
	private String memberUuid;

	public PaymentFailedEventPayload() {}

	public PaymentFailedEventPayload(final String memberUuid) {
		this.memberUuid = memberUuid;
	}
}
