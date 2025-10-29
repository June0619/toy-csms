package me.jwjung.csms.ocpp.domain.payload;

import java.time.LocalDateTime;

import lombok.Data;
import me.jwjung.csms.ocpp.domain.CorePayload;
import me.jwjung.csms.ocpp.domain.EventType;

@Data
public class StopTransaction implements CorePayload {
    private String idTag;
    private Integer meterStop;
    private String reason;
    private LocalDateTime timestamp;
    private long transactionId;
    private MeterValue transactionData;

    @Override
    public EventType getType() {
        return EventType.StopTransaction;
    }
}