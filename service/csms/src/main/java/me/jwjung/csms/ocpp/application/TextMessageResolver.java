package me.jwjung.csms.ocpp.application;

import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import me.jwjung.csms.ocpp.domain.OcppMessage;

@Slf4j
public class TextMessageResolver {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public OcppMessage resolve(TextMessage textMessage) {
		try {
			JsonNode jsonNode = objectMapper.readTree(textMessage.getPayload());
			return new OcppMessage(
					jsonNode.get(0).asInt(),
					jsonNode.get(1).asText(),
					jsonNode.get(2).asText(),
					jsonNode.get(3)
			);
		} catch (Exception e) {
			log.error("[TextMessageResolver.resolve] payload={}", textMessage.getPayload(), e);
			return null;
		}
	}

}
