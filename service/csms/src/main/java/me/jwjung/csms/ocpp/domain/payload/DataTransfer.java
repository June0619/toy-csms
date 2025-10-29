package me.jwjung.csms.ocpp.domain.payload;

import lombok.Data;
import me.jwjung.csms.ocpp.domain.CorePayload;
import me.jwjung.csms.ocpp.domain.EventType;

@Data
public class DataTransfer implements CorePayload {
	private String location;
	private Double temperature;

	@Override
	public EventType getType() {
		return EventType.DataTransfer;
	}
}
