package me.jwjung.csms.ocpp.domain.payload;

import lombok.Data;
import me.jwjung.csms.ocpp.domain.CorePayload;
import me.jwjung.csms.ocpp.domain.EventType;

@Data
public class Authorize implements CorePayload {
	private String idTag;

	@Override
	public EventType getType() {
		return EventType.Authorize;
	}
}
