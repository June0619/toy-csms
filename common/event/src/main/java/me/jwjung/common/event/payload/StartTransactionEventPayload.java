package me.jwjung.common.event.payload;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.jwjung.common.event.EventPayload;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartTransactionEventPayload implements EventPayload {
	private String transactionId;
	private String memberUuid;
	private LocalDateTime createdAt;
}
