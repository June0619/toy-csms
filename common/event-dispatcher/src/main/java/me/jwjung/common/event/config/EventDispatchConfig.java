package me.jwjung.common.event.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import me.jwjung.common.event.core.EventHandler;
import me.jwjung.common.event.core.EventDispatcher;

@Configuration
@ComponentScan("me.jwjung.common.event")
public class EventDispatchConfig {

	@Bean
	public EventDispatcher eventDispatcher(List<EventHandler> eventHandlers) {
		return new EventDispatcher(eventHandlers);
	}
}
