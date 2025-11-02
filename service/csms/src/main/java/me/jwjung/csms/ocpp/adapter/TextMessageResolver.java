package me.jwjung.csms.ocpp.adapter;

import java.util.Optional;

import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import me.jwjung.DataSerializer;
import me.jwjung.csms.ocpp.domain.MessageType;
import me.jwjung.csms.ocpp.domain.OcppMessage;

@Slf4j
public class TextMessageResolver {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public Optional<OcppMessage> resolve(TextMessage textMessage) {
		try {
			JsonNode jsonNode = objectMapper.readTree(textMessage.getPayload());
			int code = jsonNode.get(0).asInt();
			String sessionId = jsonNode.get(1).asText();
			MessageType messageType = MessageType.from(jsonNode.get(2).asText());
			JsonNode payload = jsonNode.get(3);
			OcppMessage message = new OcppMessage(
					code,
					sessionId,
					messageType,
					DataSerializer.deserialize(payload, messageType.getPayloadClass())
			);
			return Optional.of(message);
		} catch (Exception e) {
			log.error("[TextMessageResolver.resolve] payload={}", textMessage.getPayload(), e);
			return Optional.empty();
		}
	}

}
