package me.jwjung.csms.ocpp.domain.payload;

import lombok.ToString;
import me.jwjung.csms.ocpp.domain.MessageType;

@ToString
public class MeterValuePayload implements CorePayload {

    private Long chargingAmount;

    @Override
    public MessageType getType() {
        return MessageType.MeterValue;
    }
}