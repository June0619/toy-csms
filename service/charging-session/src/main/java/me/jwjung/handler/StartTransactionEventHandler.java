package me.jwjung.handler;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventType;
import me.jwjung.common.event.payload.StartTransactionEventPayload;
import me.jwjung.domain.Session;
import me.jwjung.repository.SessionRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartTransactionEventHandler implements EventHandler<StartTransactionEventPayload> {
	private final SessionRepository sessionRepository;

	@Override
	public void handle(Event<StartTransactionEventPayload> event) {
		StartTransactionEventPayload payload = event.getPayload();
		sessionRepository.save(
				Session.create(
						payload.getTransactionId(), payload.getMemberUuid()
				)
		);
	}

	@Override
	public boolean supports(Event<StartTransactionEventPayload> event) {
		return EventType.START_TRANSACTION == event.getType();
	}
}
