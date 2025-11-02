package me.jwjung.csms.ocpp.domain.payload;

import java.time.LocalDateTime;

import lombok.Data;
import me.jwjung.csms.ocpp.domain.MessageType;

@Data
public class StartTransactionPayload implements CorePayload {
    private int connectorId;
    private String idTag;
    private LocalDateTime timestamp;
    private int meterStart;
    private String transactionId;

    @Override
    public MessageType getType() {
        return MessageType.StopTransaction;
    }
}
