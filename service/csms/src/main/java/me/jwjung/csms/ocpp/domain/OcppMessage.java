package me.jwjung.csms.ocpp.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OcppMessage {
	private Integer code;
	private String sessionId;
	private EventType eventType;
	private Object payload;

	public OcppMessage(Integer code, String sessionId, String eventType, Object payload) {
		this.code = code;
		this.sessionId = sessionId;
		this.eventType = EventType.valueOf(eventType);
		this.payload = payload;
	}


}
