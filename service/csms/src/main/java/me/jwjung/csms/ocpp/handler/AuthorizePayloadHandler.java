package me.jwjung.csms.ocpp.handler;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.jwjung.csms.ocpp.domain.MessageType;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.AuthorizeOcppPayload;

@Slf4j
@Component
public class AuthorizePayloadHandler implements PayloadHandler<AuthorizeOcppPayload> {
	@Override
	public boolean isSupport(OcppMessage<AuthorizeOcppPayload> ocppMessage) {
		return MessageType.Authorize == ocppMessage.getMessageType();
	}

	@Override
	public void handle(OcppMessage<AuthorizeOcppPayload> ocppMessage) {
		AuthorizeOcppPayload payload = ocppMessage.getPayload();
		log.info("payload: {}", payload);
	}
}
