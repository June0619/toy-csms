package me.jwjung.handler;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.jwjung.common.event.Event;
import me.jwjung.common.event.EventType;
import me.jwjung.common.event.core.EventHandler;
import me.jwjung.common.event.payload.ChargingSessionCompletedEventPayload;
import me.jwjung.common.event.payload.PaymentFailedEventPayload;
import me.jwjung.common.event.producer.KafkaMessagePublisher;
import me.jwjung.common.snowflake.Snowflake;
import me.jwjung.domain.BillingRequest;
import me.jwjung.service.NotEnoughBalanceException;
import me.jwjung.service.PaymentGateway;

@Component
@RequiredArgsConstructor
public class ChargingSessionCompletedEventHandler implements EventHandler<ChargingSessionCompletedEventPayload> {

	private final PaymentGateway paymentGateway;
	private final KafkaMessagePublisher messagePublisher;
	private final Snowflake snowflake = new Snowflake();

	@Override
	public void handle(Event<ChargingSessionCompletedEventPayload> event) {
		final ChargingSessionCompletedEventPayload payload = event.getPayload();

		try {
			paymentGateway.requestPayment(
					new BillingRequest(
							payload.getTransactionId(),
							payload.getMemberUuid(),
							payload.getBillingFee()
					)
			);
		} catch (NotEnoughBalanceException ex) {
			messagePublisher.publish(
					Event.of(
							snowflake.nextId(),
							EventType.PAYMENT_FAILED,
							new PaymentFailedEventPayload(payload.getMemberUuid())
					)
			);
		}
	}

	@Override
	public boolean supports(Event<ChargingSessionCompletedEventPayload> event) {
		return EventType.CHARGING_SESSION_COMPLETED == event.getType();
	}
}
