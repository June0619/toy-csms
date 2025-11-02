package me.jwjung.csms.ocpp.handler;

import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.CorePayload;

public interface PayloadHandler<T extends CorePayload> {
	boolean isSupport(OcppMessage<T> ocppMessage);
	void handle(OcppMessage<T> ocppMessage);
}
