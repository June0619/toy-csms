package me.jwjung.common.event.core;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventPayload;

@Component
@RequiredArgsConstructor
public class EventDispatcher {
	private final List<EventHandler> eventHandlers;

	public void dispatch(Event<EventPayload> event) {
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
