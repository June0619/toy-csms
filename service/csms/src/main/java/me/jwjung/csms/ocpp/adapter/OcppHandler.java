package me.jwjung.csms.ocpp.adapter;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;
import me.jwjung.csms.ocpp.application.OcppMessageResolver;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.application.TextMessageResolver;

@Slf4j
@Component
public class OcppHandler extends TextWebSocketHandler {

	private final TextMessageResolver textMessageResolver = new TextMessageResolver();
	private final OcppMessageResolver ocppMessageResolver = new OcppMessageResolver();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("✅ Connected: {}", session.getId());
		session.sendMessage(new TextMessage("Hello! WebSocket connection established."));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage originalMessage) {
		final OcppMessage message = textMessageResolver.resolve(originalMessage);
		if (message == null) {
			return;
		}
		ocppMessageResolver.resolve(message).ifPresent(payload -> {

		});
		log.info("[OcppHandler.handleTextMessage] sessionId={}, message={}", session.getId(), message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		log.error("❌ Disconnected: {}", session.getId());
	}
}
