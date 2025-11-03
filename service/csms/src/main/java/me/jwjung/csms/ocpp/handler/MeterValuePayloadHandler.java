package me.jwjung.csms.ocpp.handler;

import org.springframework.stereotype.Component;

import me.jwjung.csms.ocpp.domain.MessageType;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.MeterValueOcppPayload;

@Component
public class MeterValuePayloadHandler implements PayloadHandler<MeterValueOcppPayload> {

	@Override
	public void handle(OcppMessage<MeterValueOcppPayload> ocppMessage) {

	}

	@Override
	public boolean isSupport(OcppMessage<MeterValueOcppPayload> ocppMessage) {
		return MessageType.MeterValue == ocppMessage.getMessageType();
	}
}
