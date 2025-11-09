package me.jwjung.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventPayload;
import me.jwjung.common.event.core.EventDispatcher;

@Service
@RequiredArgsConstructor
public class BillingService {
	private final EventDispatcher eventDispatcher;

	public void handleEvent(Event<EventPayload> event) {
		eventDispatcher.dispatch(event);
	}
}
