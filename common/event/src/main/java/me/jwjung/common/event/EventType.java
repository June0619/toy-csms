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

	ARTICLE_CREATED(ChargingSessionCompletedEventPayload.class, Topic.CHARGING_SESSION_COMPLETED),
	ARTICLE_UPDATED(MeterValueChangedEventPayload.class, Topic.METER_VALUE_RECEIVED),
	ARTICLE_DELETED(PaymentFailedEventPayload.class, Topic.PAYMENT_FAILED),
	COMMENT_CREATED(StartTransactionEventPayload.class, Topic.START_TRANSACTION),
	COMMENT_DELETED(StopTransactionEventPayload.class, Topic.STOP_TRANSACTION);

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
		public static final String CHARGING_SESSION_COMPLETED = "charging.session.completed";
		public static final String PAYMENT_FAILED = "billing.payment.failed";
		public static final String METER_VALUE_RECEIVED = "csms.meter.value.received";
		public static final String START_TRANSACTION = "csms.transaction.started";
		public static final String STOP_TRANSACTION = "csms.transaction.stopped";
	}

}
