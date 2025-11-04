package me.jwjung.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventPayload;
import me.jwjung.common.event.EventType;
import me.jwjung.service.SessionService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsmsEventConsumer {

	private final SessionService sessionService;

	@KafkaListener(topics = {
			EventType.Topic.CSMS_TRANSACTION
	})
	public void listen(String message, Acknowledgment ack) {
		log.info("[CsmsEventConsumer.listen] received message={}", message);
		Event<EventPayload> event = Event.fromJson(message);
		if (event != null) {
			sessionService.handleEvent(event);
		}
		ack.acknowledge();
	}
}
