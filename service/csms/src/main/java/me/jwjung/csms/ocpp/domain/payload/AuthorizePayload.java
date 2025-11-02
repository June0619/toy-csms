package me.jwjung.csms.ocpp.domain.payload;

import lombok.Data;
import me.jwjung.csms.ocpp.domain.MessageType;

@Data
public class AuthorizePayload implements CorePayload {
	private String idTag;

	@Override
	public MessageType getType() {
		return MessageType.Authorize;
	}
}
