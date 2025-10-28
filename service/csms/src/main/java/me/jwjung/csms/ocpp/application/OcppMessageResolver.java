package me.jwjung.csms.ocpp.application;

import java.util.Map;
import java.util.Optional;

import me.jwjung.DataSerializer;
import me.jwjung.csms.ocpp.domain.OcppMessage;
import me.jwjung.csms.ocpp.domain.EventType;
import me.jwjung.csms.ocpp.domain.payload.Authorize;
import me.jwjung.csms.ocpp.domain.CorePayload;
import me.jwjung.csms.ocpp.domain.payload.StartTransaction;
import me.jwjung.csms.ocpp.domain.payload.StopTransaction;

public class OcppMessageResolver {

	private final Map<EventType, Class<? extends CorePayload>> mapping = Map.of(
			EventType.Authorize, Authorize.class,
			EventType.StartTransaction, StartTransaction.class,
			EventType.StopTransaction, StopTransaction.class
	);

	public Optional<CorePayload> resolve(OcppMessage ocppMessage) {
		Class<? extends CorePayload> clazz = mapping.get(ocppMessage.getEventType());
		if (clazz == null) {
			return Optional.empty();
		}
		return Optional.of(DataSerializer.deserialize(ocppMessage.getPayload(), clazz));
	}
}
