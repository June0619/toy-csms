package me.jwjung.csms.ocpp.domain.payload;

import java.time.LocalDateTime;

import me.jwjung.csms.ocpp.domain.CorePayload;

public class StopTransaction implements CorePayload {
    private String idTag;
    private Integer meterStop;
    private String reason;
    private LocalDateTime timestamp;
    private long transactionId;
    private MeterValue transactionData;
}