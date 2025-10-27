package me.jwjung.csms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import me.jwjung.csms.handler.HelloHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private final HelloHandler helloHandler;

	public WebSocketConfig(final HelloHandler helloHandler) {
		this.helloHandler = helloHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(helloHandler, "/ws")
				.setAllowedOrigins("*");
	}
}
