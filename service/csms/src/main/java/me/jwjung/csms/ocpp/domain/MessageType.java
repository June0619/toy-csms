package me.jwjung.csms.ocpp.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.csms.ocpp.domain.payload.AuthorizeOcppPayload;
import me.jwjung.csms.ocpp.domain.payload.CorePayload;
import me.jwjung.csms.ocpp.domain.payload.MeterValueOcppPayload;
import me.jwjung.csms.ocpp.domain.payload.StartTransactionOcppPayload;
import me.jwjung.csms.ocpp.domain.payload.StopTransactionOcppPayload;

@Slf4j
@Getter
public enum MessageType {
	Authorize(AuthorizeOcppPayload.class),
	MeterValue(MeterValueOcppPayload.class),
	StartTransaction(StartTransactionOcppPayload.class),
	StopTransaction(StopTransactionOcppPayload.class);

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
