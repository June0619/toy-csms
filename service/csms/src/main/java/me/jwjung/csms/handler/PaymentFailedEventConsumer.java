package me.jwjung.csms.handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventPayload;
import me.jwjung.common.event.EventType;
import me.jwjung.common.event.payload.PaymentFailedEventPayload;
import me.jwjung.csms.authorization.BlackListService;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFailedEventConsumer {

	private final BlackListService blackListService;

	@KafkaListener(topics = {
			EventType.Topic.PAYMENT_FAILED
	})
	public void listen(String message, Acknowledgment ack) {
		log.info("[CsmsEventConsumer.listen] received message={}", message);
		Event<EventPayload> event = Event.fromJson(message);
		if (event != null && event.getType() == EventType.PAYMENT_FAILED) {
			PaymentFailedEventPayload payload = (PaymentFailedEventPayload)event.getPayload();
			blackListService.save(payload.getMemberUuid());
		}
		ack.acknowledge();
	}

}
