package me.jwjung.csms.ocpp.handler;

import org.springframework.stereotype.Component;

import me.jwjung.csms.ocpp.domain.MessageType;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.MeterValuePayload;

@Component
public class MeterValuePayloadHandler implements PayloadHandler<MeterValuePayload> {

	@Override
	public void handle(OcppMessage<MeterValuePayload> ocppMessage) {

	}

	@Override
	public boolean isSupport(OcppMessage<MeterValuePayload> ocppMessage) {
		return MessageType.MeterValue == ocppMessage.getMessageType();
	}
}
