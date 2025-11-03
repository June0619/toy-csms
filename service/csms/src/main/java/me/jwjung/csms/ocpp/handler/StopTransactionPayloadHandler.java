package me.jwjung.csms.ocpp.handler;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.jwjung.csms.ocpp.domain.MessageType;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.StopTransactionOcppPayload;

@Slf4j
@Component
public class StopTransactionPayloadHandler implements PayloadHandler<StopTransactionOcppPayload> {
	@Override
	public boolean isSupport(OcppMessage<StopTransactionOcppPayload> ocppMessage) {
		return MessageType.StopTransaction == ocppMessage.getMessageType();
	}

	@Override
	public void handle(OcppMessage<StopTransactionOcppPayload> ocppMessage) {
		StopTransactionOcppPayload payload = ocppMessage.getPayload();
		log.info("payload: {}", payload);
	}
}
