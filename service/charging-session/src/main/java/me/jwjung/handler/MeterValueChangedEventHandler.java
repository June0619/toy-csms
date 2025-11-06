package me.jwjung.handler;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventType;
import me.jwjung.common.event.payload.MeterValueChangedEventPayload;
import me.jwjung.repository.SessionRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class MeterValueChangedEventHandler implements EventHandler<MeterValueChangedEventPayload> {
	private final SessionRepository sessionRepository;

	@Override
	public void handle(Event<MeterValueChangedEventPayload> event) {
		MeterValueChangedEventPayload payload = event.getPayload();
		sessionRepository.updateMeterValue(
				payload.getTransactionId(),
				payload.getMeterValueAmount()
		);
	}

	@Override
	public boolean supports(Event<MeterValueChangedEventPayload> event) {
		return EventType.METER_VALUE_CHANGED == event.getType();
	}
}
