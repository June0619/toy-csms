package me.jwjung.common.event.producer;

import java.util.concurrent.TimeUnit;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessagePublisher {
	private final KafkaTemplate<String, String> kafkaTemplate;

	public void publish(Event<?> event) {
		try {
			kafkaTemplate.send(
					event.getType().getTopic(),
					event.toJson()
			).get(1, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("[MessageRelay.publishEvent] outbox={}", event, e);
		}
	}
}
