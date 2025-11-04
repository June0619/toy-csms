package me.jwjung.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventPayload;
import me.jwjung.handler.EventHandler;

@Service
@RequiredArgsConstructor
public class SessionService {
	private final List<EventHandler> eventHandlers;

	public void handleEvent(Event<EventPayload> event) {
		EventHandler<EventPayload> handler = findEventHandler(event);
		handler.handle(event);
	}

	private EventHandler<EventPayload> findEventHandler(Event<EventPayload> event) {
		return eventHandlers.stream()
				.filter(eventHandler -> eventHandler.supports(event))
				.findAny()
				.orElseThrow();
	}

}
