package me.jwjung.csms.ocpp.domain.payload;

import lombok.ToString;
import me.jwjung.csms.ocpp.domain.CorePayload;
import me.jwjung.csms.ocpp.domain.EventType;

@ToString
public class MeterValue implements CorePayload {

    private Long chargingAmount;

    @Override
    public EventType getType() {
        return EventType.MeterValue;
    }
}