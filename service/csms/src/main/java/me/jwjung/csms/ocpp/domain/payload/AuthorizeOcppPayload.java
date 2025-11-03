package me.jwjung.csms.ocpp.domain.payload;

import lombok.Data;
import me.jwjung.csms.ocpp.domain.MessageType;

@Data
public class AuthorizeOcppPayload implements CorePayload {
	private String idTag;

	@Override
	public MessageType getType() {
		return MessageType.Authorize;
	}
}
