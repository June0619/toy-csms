package me.jwjung.common.event.core;

import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
	void handle(Event<T> event);
	boolean supports(Event<T> event);
}
