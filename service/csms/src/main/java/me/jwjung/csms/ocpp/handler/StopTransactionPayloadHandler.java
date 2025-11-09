package me.jwjung.csms.ocpp.handler;


import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventPayload;
import me.jwjung.common.event.EventType;
import me.jwjung.common.event.payload.StopTransactionEventPayload;
import me.jwjung.common.event.producer.KafkaMessagePublisher;
import me.jwjung.common.snowflake.Snowflake;
import me.jwjung.csms.ocpp.domain.MessageType;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.StopTransactionOcppPayload;

@Slf4j
@Component
@RequiredArgsConstructor
public class StopTransactionPayloadHandler implements PayloadHandler<StopTransactionOcppPayload> {
	private final KafkaMessagePublisher messagePublisher;
	private final Snowflake snowflake = new Snowflake();

	@Override
	public boolean isSupport(OcppMessage<StopTransactionOcppPayload> ocppMessage) {
		return MessageType.StopTransaction == ocppMessage.getMessageType();
	}

	@Override
	public void handle(OcppMessage<StopTransactionOcppPayload> ocppMessage) {
		StopTransactionOcppPayload payload = ocppMessage.getPayload();
		log.info("payload: {}", payload);
		final StopTransactionEventPayload eventPayload = new StopTransactionEventPayload(
				payload.getTransactionId(),
				payload.getMeterValueAmount()
		);

		Event<EventPayload> event = Event.of(
				snowflake.nextId(),
				EventType.STOP_TRANSACTION,
				eventPayload
		);
		messagePublisher.publish(event);
	}
}
