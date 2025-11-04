package me.jwjung.csms.ocpp.domain.payload;

import lombok.Data;
import me.jwjung.csms.ocpp.domain.MessageType;

@Data
public class StartTransactionOcppPayload implements CorePayload {
    private String idTag;
    private String transactionId;

    @Override
    public MessageType getType() {
        return MessageType.StartTransaction;
    }
}
