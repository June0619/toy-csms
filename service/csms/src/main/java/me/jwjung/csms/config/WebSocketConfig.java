package me.jwjung.csms.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import me.jwjung.csms.ocpp.adapter.OcppHandler;
import me.jwjung.csms.ocpp.handler.PayloadHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private final OcppHandler ocppHandler;

	public WebSocketConfig(final OcppHandler ocppHandler) {
		this.ocppHandler = ocppHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(ocppHandler, "/ws")
				.setAllowedOrigins("*");
	}

}
