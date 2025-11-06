package me.jwjung.csms.ocpp.domain.payload;

import lombok.Data;
import me.jwjung.csms.ocpp.domain.MessageType;

@Data
public class StopTransactionOcppPayload implements CorePayload {
    private String idTag;
    private String transactionId;
    private Double meterValueAmount;

    @Override
    public MessageType getType() {
        return MessageType.StopTransaction;
    }
}