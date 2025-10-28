package me.jwjung.csms.ocpp.domain.payload;

import java.time.LocalDateTime;

import me.jwjung.csms.ocpp.domain.CorePayload;

public class StartTransaction implements CorePayload {
    private int connectorId;
    private String idTag;
    private LocalDateTime timestamp;
    private int meterStart;
}
