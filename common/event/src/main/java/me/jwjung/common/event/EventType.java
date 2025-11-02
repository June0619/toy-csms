package me.jwjung.common.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.common.event.payload.ChargingSessionCompletedEventPayload;
import me.jwjung.common.event.payload.PaymentFailedEventPayload;
import me.jwjung.common.event.payload.StartTransactionEventPayload;
import me.jwjung.common.event.payload.StopTransactionEventPayload;
import me.jwjung.common.event.payload.MeterValueChangedEventPayload;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {

	CHARGING_SESSION_COMPLETED(ChargingSessionCompletedEventPayload.class, Topic.CHARGING_SESSION_COMPLETED),
	PAYMENT_FAILED(PaymentFailedEventPayload.class, Topic.PAYMENT_FAILED),
	METER_VALUE_CHANGED(MeterValueChangedEventPayload.class, Topic.CSMS_TRANSACTION),
	START_TRANSACTION(StartTransactionEventPayload.class, Topic.CSMS_TRANSACTION),
	STOP_TRANSACTION(StopTransactionEventPayload.class, Topic.CSMS_TRANSACTION);

	private final Class<? extends EventPayload> payloadClass;
	private final String topic;

	public static EventType from(String type) {
		try {
			return valueOf(type);
		} catch (Exception e) {
			log.error("[EventType.from] type={}", type, e);
			return null;
		}
	}

	public static class Topic {
		public static final String CHARGING_SESSION_COMPLETED = "charging.session";
		public static final String PAYMENT_FAILED = "billing.payment";
		public static final String CSMS_TRANSACTION = "csms.transaction";
	}

}
