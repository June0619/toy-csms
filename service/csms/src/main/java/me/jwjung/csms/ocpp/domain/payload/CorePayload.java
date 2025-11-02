package me.jwjung.csms.ocpp.domain.payload;

import me.jwjung.csms.ocpp.domain.MessageType;

public interface CorePayload {
	MessageType getType();
}
