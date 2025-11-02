package me.jwjung.csms.ocpp.domain;

import lombok.Getter;
import lombok.ToString;
import me.jwjung.csms.ocpp.domain.payload.CorePayload;

@Getter
@ToString
public class OcppMessage<T extends CorePayload> {
	private Integer code;
	private String sessionId;
	private MessageType messageType;
	private T payload;

	public OcppMessage(Integer code, String sessionId, MessageType messageType, T payload) {
		this.code = code;
		this.sessionId = sessionId;
		this.messageType = messageType;
		this.payload = payload;
	}

}
