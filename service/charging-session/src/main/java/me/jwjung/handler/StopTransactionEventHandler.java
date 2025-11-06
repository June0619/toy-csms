package me.jwjung.handler;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventType;
import me.jwjung.common.event.payload.StartTransactionEventPayload;
import me.jwjung.common.event.payload.StopTransactionEventPayload;
import me.jwjung.repository.SessionRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class StopTransactionEventHandler implements EventHandler<StopTransactionEventPayload> {
	private final SessionRepository sessionRepository;

	@Override
	public void handle(Event<StopTransactionEventPayload> event) {
		StopTransactionEventPayload payload = event.getPayload();
		String transactionId = payload.getTransactionId();
		sessionRepository.stopSession(transactionId);
	}

	@Override
	public boolean supports(Event<StopTransactionEventPayload> event) {
		return EventType.STOP_TRANSACTION == event.getType();
	}
}
