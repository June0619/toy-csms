package me.jwjung;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class KstLocalDateTimeModule extends SimpleModule {

    public KstLocalDateTimeModule() {
        super("KstLocalDateTimeModule");
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeKstDeserializer());
    }

    private static class LocalDateTimeKstDeserializer extends JsonDeserializer<LocalDateTime> {

        private static final ZoneId ASIA_SEOUL = ZoneId.of("Asia/Seoul");

        private static final DateTimeFormatter BASE_FORMATTER = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .optionalStart()
                .appendOffset("+HH:MM", "Z")
                .optionalEnd()
                .toFormatter();

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            final String rawValue = p.getText();

            try {
                var parsed = BASE_FORMATTER.parse(rawValue);

                if (parsed.isSupported(ChronoField.OFFSET_SECONDS)) {
                    Instant instant = Instant.from(parsed);
                    return LocalDateTime.ofInstant(instant, ASIA_SEOUL);
                }
                return LocalDateTime.from(parsed);

            } catch (Exception ex) {
                throw new IllegalStateException(
                        "KST 기준 시간 변환 중 오류 발생. 입력 값: [" + rawValue + "]", ex
                );
            }
        }
    }
}
