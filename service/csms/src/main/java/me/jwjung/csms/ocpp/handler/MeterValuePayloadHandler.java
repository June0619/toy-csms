package me.jwjung.csms.ocpp.handler;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventPayload;
import me.jwjung.common.event.EventType;
import me.jwjung.common.event.payload.MeterValueChangedEventPayload;
import me.jwjung.common.snowflake.Snowflake;
import me.jwjung.csms.ocpp.domain.MessageType;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.MeterValueOcppPayload;
import me.jwjung.csms.publisher.KafkaMessagePublisher;

@Slf4j
@Component
@RequiredArgsConstructor
public class MeterValuePayloadHandler implements PayloadHandler<MeterValueOcppPayload> {
	private final KafkaMessagePublisher messagePublisher;
	private final Snowflake snowflake = new Snowflake();

	@Override
	public void handle(OcppMessage<MeterValueOcppPayload> ocppMessage) {
		MeterValueOcppPayload payload = ocppMessage.getPayload();
		log.info("payload: {}", payload);
		MeterValueChangedEventPayload eventPayload = new MeterValueChangedEventPayload(
				payload.getTransactionId(),
				payload.getChargingAmount()
		);

		Event<EventPayload> event = Event.of(
				snowflake.nextId(),
				EventType.START_TRANSACTION,
				eventPayload
		);
		messagePublisher.publish(event);
	}

	@Override
	public boolean isSupport(OcppMessage<MeterValueOcppPayload> ocppMessage) {
		return MessageType.MeterValue == ocppMessage.getMessageType();
	}
}
