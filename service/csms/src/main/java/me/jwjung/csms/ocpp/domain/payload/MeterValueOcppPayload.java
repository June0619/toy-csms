package me.jwjung.csms.ocpp.domain.payload;

import lombok.Data;
import lombok.ToString;
import me.jwjung.csms.ocpp.domain.MessageType;

@Data
@ToString
public class MeterValueOcppPayload implements CorePayload {
    private String transactionId;
    private Double meterValueAmount;

    @Override
    public MessageType getType() {
        return MessageType.MeterValue;
    }
}