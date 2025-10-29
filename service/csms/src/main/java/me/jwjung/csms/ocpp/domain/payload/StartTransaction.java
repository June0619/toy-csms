package me.jwjung.csms.ocpp.domain.payload;

import java.time.LocalDateTime;

import lombok.Data;
import me.jwjung.csms.ocpp.domain.CorePayload;
import me.jwjung.csms.ocpp.domain.EventType;

@Data
public class StartTransaction implements CorePayload {
    private int connectorId;
    private String idTag;
    private LocalDateTime timestamp;
    private int meterStart;
    private String transactionId;

    @Override
    public EventType getType() {
        return EventType.StopTransaction;
    }
}
