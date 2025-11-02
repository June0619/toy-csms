package me.jwjung.csms.ocpp.handler;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.jwjung.csms.ocpp.domain.MessageType;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.StopTransactionPayload;

@Slf4j
@Component
public class StopTransactionPayloadHandler implements PayloadHandler<StopTransactionPayload> {
	@Override
	public boolean isSupport(OcppMessage<StopTransactionPayload> ocppMessage) {
		return MessageType.StopTransaction == ocppMessage.getMessageType();
	}

	@Override
	public void handle(OcppMessage<StopTransactionPayload> ocppMessage) {
		StopTransactionPayload payload = ocppMessage.getPayload();
		log.info("payload: {}", payload);
	}
}
