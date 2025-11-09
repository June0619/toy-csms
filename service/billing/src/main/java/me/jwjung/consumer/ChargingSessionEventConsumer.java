package me.jwjung.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventPayload;
import me.jwjung.common.event.EventType;
import me.jwjung.service.BillingService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChargingSessionEventConsumer {

	private final BillingService billingService;

	@KafkaListener(topics = {
			EventType.Topic.CHARGING_SESSION_COMPLETED
	})
	public void listen(String message) {
		Event<EventPayload> eventPayloadEvent = Event.fromJson(message);
		billingService.handleEvent(eventPayloadEvent);
	}



}
