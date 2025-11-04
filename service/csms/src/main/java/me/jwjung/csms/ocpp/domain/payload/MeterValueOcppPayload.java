package me.jwjung.csms.ocpp.domain.payload;

import lombok.Data;
import lombok.ToString;
import me.jwjung.csms.ocpp.domain.MessageType;

@Data
@ToString
public class MeterValueOcppPayload implements CorePayload {
    private String transactionId;
    private Double chargingAmount;

    @Override
    public MessageType getType() {
        return MessageType.MeterValue;
    }
}