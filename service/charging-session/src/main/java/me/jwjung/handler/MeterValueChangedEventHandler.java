package me.jwjung.handler;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventType;
import me.jwjung.common.event.core.EventHandler;
import me.jwjung.common.event.payload.MeterValueChangedEventPayload;
import me.jwjung.domain.Session;
import me.jwjung.repository.SessionRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class MeterValueChangedEventHandler implements EventHandler<MeterValueChangedEventPayload> {
	private final SessionRepository sessionRepository;

	@Override
	public void handle(Event<MeterValueChangedEventPayload> event) {
		MeterValueChangedEventPayload payload = event.getPayload();
		Session session = sessionRepository.findById(payload.getTransactionId())
				.orElseThrow(() -> new IllegalStateException("Session not found"));
		session.updateNewMeterValue(payload.getMeterValueAmount());
		sessionRepository.save(session);
		log.info("Session updated: {}", session);
	}

	@Override
	public boolean supports(Event<MeterValueChangedEventPayload> event) {
		return EventType.METER_VALUE_CHANGED == event.getType();
	}
}
