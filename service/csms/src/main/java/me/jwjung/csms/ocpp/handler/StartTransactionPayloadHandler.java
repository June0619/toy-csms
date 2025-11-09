package me.jwjung.csms.ocpp.handler;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventPayload;
import me.jwjung.common.event.EventType;
import me.jwjung.common.event.payload.StartTransactionEventPayload;
import me.jwjung.common.event.producer.KafkaMessagePublisher;
import me.jwjung.common.snowflake.Snowflake;
import me.jwjung.csms.ocpp.domain.MessageType;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.StartTransactionOcppPayload;


@Slf4j
@Component
@RequiredArgsConstructor
public class StartTransactionPayloadHandler implements PayloadHandler<StartTransactionOcppPayload> {
	private final KafkaMessagePublisher messagePublisher;
	private final Snowflake snowflake = new Snowflake();

	@Override
	public boolean isSupport(OcppMessage<StartTransactionOcppPayload> ocppMessage) {
		return MessageType.StartTransaction == ocppMessage.getMessageType();
	}

	@Override
	public void handle(OcppMessage<StartTransactionOcppPayload> ocppMessage) {
		StartTransactionOcppPayload payload = ocppMessage.getPayload();
		log.info("payload: {}", payload);
		StartTransactionEventPayload eventPayload = new StartTransactionEventPayload(
				payload.getTransactionId(),
				payload.getIdTag(),
				LocalDateTime.now()
		);

		Event<EventPayload> event = Event.of(
				snowflake.nextId(),
				EventType.START_TRANSACTION,
				eventPayload
		);
		messagePublisher.publish(event);
	}
}
