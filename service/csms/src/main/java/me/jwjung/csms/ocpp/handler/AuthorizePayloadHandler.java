package me.jwjung.csms.ocpp.handler;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.jwjung.csms.ocpp.domain.MessageType;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.AuthorizePayload;

@Slf4j
@Component
public class AuthorizePayloadHandler implements PayloadHandler<AuthorizePayload> {
	@Override
	public boolean isSupport(OcppMessage<AuthorizePayload> ocppMessage) {
		return MessageType.Authorize == ocppMessage.getMessageType();
	}

	@Override
	public void handle(OcppMessage<AuthorizePayload> ocppMessage) {
		AuthorizePayload payload = ocppMessage.getPayload();
		log.info("payload: {}", payload);
	}
}
