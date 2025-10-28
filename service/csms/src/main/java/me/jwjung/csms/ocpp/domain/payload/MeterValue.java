package me.jwjung.csms.ocpp.domain.payload;

import java.time.LocalDateTime;
import java.util.List;

import me.jwjung.csms.ocpp.domain.CorePayload;

public class MeterValue implements CorePayload {
    List<MeterValuePayload> meterValuePayloads;

    static class MeterValuePayload {
        LocalDateTime timestamp;
        List<SampledValue> sampledValue;
    }

    public static class SampledValue {
        String value;
        String context;
        String format;
        String measurand;
        String phase;
        String location;
        String unit;
    }
}