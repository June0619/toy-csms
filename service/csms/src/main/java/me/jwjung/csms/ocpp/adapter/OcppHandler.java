package me.jwjung.csms.ocpp.adapter;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.payload.CorePayload;
import me.jwjung.csms.ocpp.handler.PayloadHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class OcppHandler extends TextWebSocketHandler {

	private final TextMessageResolver textMessageResolver = new TextMessageResolver();
	private final List<PayloadHandler> payloadHandlers;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("✅ Connected: {}", session.getId());
		session.sendMessage(new TextMessage("Hello! WebSocket connection established."));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage originalMessage) {
		textMessageResolver.resolve(originalMessage)
				.ifPresentOrElse(this::handleMessage,
						() -> {
							log.error("not supported message");
						});
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		log.error("❌ Disconnected: {}", session.getId());
	}

	private void handleMessage(OcppMessage<CorePayload> message) {
		payloadHandlers.stream()
				.filter(handler -> handler.isSupport(message))
				.forEach(handler -> handler.handle(message));
	}
}
