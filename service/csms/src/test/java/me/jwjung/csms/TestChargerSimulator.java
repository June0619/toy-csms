package me.jwjung.csms;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class TestChargerSimulator {
	private static final String WS_URL = "ws://localhost:9010/ws";
	private static final int TOTAL_CLIENTS = 1;
	private static final int METER_COUNT = 5;
	private static final int RAMP_DELAY_MS = 10;
	private static final int METER_INTERVAL_MS = 2000;

	private final HttpClient httpClient = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(5))
			.build();

	private final Random random = new Random();

	@Test
	void ocpp_bulk_websocket_load_test() throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(Math.min(TOTAL_CLIENTS, 200));

		List<CompletableFuture<Void>> tasks = IntStream.rangeClosed(1, TOTAL_CLIENTS)
				.mapToObj(i -> CompletableFuture.runAsync(() -> runClient(i), pool))
				.toList();

		CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new)).join();

		pool.shutdown();
		assertThat(pool.awaitTermination(30, TimeUnit.SECONDS)).isTrue();
	}

	private void runClient(int idx) {
		String clientId = String.format("custom-%03d", idx);
		String idTag = "fail-member-uuid-" + random.nextInt(999);
		String transactionId = "transaction-" + random.nextInt(999);

		try {
			WebSocket ws = httpClient.newWebSocketBuilder()
					.connectTimeout(Duration.ofSeconds(5))
					.buildAsync(URI.create(WS_URL), new SimpleListener(clientId))
					.join();

			// 1️⃣ StartTransaction
			send(ws, frame(2, clientId, "StartTransaction",
					"""
					{
					  "idTag": "%s",
					  "transactionId": "%s"
					}
					""".formatted(idTag, transactionId)
			));
			Thread.sleep(100);

			// 2️⃣ MeterValue (0.0부터 시작)
			double meterValue = 0.0;
			for (int j = 1; j <= METER_COUNT; j++) {
				if (j == 1) {
					meterValue = 0.0; // 첫 번째는 반드시 0.0
				} else {
					meterValue += (random.nextDouble() * 1.4 + 0.1); // 0.1 ~ 1.5 증가
				}

				send(ws, frame(2, clientId, "MeterValue",
						"""
						{
						  "meterValueAmount": %.2f,
						  "transactionId": "%s"
						}
						""".formatted(meterValue, transactionId)
				));
				Thread.sleep(METER_INTERVAL_MS);
			}

			// 3️⃣ StopTransaction
			send(ws, frame(2, clientId, "StopTransaction",
					"""
					{
					  "transactionId": "%s",
					  "idTag": "%s",
					  "meterValueAmount": %.2f
					}
					""".formatted(transactionId, idTag, meterValue)
			));

			ws.sendClose(WebSocket.NORMAL_CLOSURE, "bye").join();

		} catch (Exception e) {
			System.err.printf("[%s] 연결 실패: %s%n", clientId, e.getMessage());
		}
	}

	private void send(WebSocket ws, String payload) {
		ws.sendText(payload, true).join();
	}

	private String frame(int type, String uid, String action, String body) {
		return String.format("[%d, \"%s\", \"%s\", %s]", type, uid, action, body);
	}

	static class SimpleListener implements WebSocket.Listener {
		private final String clientId;
		public SimpleListener(String clientId) { this.clientId = clientId; }

		@Override
		public void onOpen(WebSocket webSocket) {
			System.out.printf("[%s] 연결 완료%n", clientId);
			webSocket.request(1);
		}

		@Override
		public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
			// 응답 필요 시 추가 로깅 가능
			// System.out.printf("[%s] 수신: %s%n", clientId, data);
			webSocket.request(1);
			return CompletableFuture.completedFuture(null);
		}

		@Override
		public void onError(WebSocket webSocket, Throwable error) {
			System.err.printf("[%s] 에러: %s%n", clientId, error.getMessage());
		}
	}
}
