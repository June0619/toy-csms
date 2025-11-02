package me.jwjung.csms.ocpp.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.csms.ocpp.domain.payload.AuthorizePayload;
import me.jwjung.csms.ocpp.domain.payload.CorePayload;
import me.jwjung.csms.ocpp.domain.payload.MeterValuePayload;
import me.jwjung.csms.ocpp.domain.payload.StartTransactionPayload;
import me.jwjung.csms.ocpp.domain.payload.StopTransactionPayload;

@Slf4j
@Getter
public enum MessageType {
	Authorize(AuthorizePayload.class),
	MeterValue(MeterValuePayload.class),
	StartTransaction(StartTransactionPayload.class),
	StopTransaction(StopTransactionPayload.class);

	private final Class<? extends CorePayload> payloadClass;

	public static MessageType from(String type) {
		try {
			return valueOf(type);
		} catch (Exception e) {
			log.error("[EventType.from] type={}", type, e);
			return null;
		}
	}

	MessageType(final Class<? extends CorePayload> payloadClass) {
		this.payloadClass = payloadClass;
	}
}
