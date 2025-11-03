package me.jwjung.csms.ocpp.domain.payload;

import java.time.LocalDateTime;

import lombok.Data;
import me.jwjung.csms.ocpp.domain.MessageType;

@Data
public class StopTransactionOcppPayload implements CorePayload {
    private String idTag;
    private Integer meterStop;
    private String reason;
    private LocalDateTime timestamp;
    private long transactionId;
    private MeterValueOcppPayload transactionData;

    @Override
    public MessageType getType() {
        return MessageType.StopTransaction;
    }
}