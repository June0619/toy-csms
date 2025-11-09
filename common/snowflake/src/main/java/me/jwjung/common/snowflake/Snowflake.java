package me.jwjung.common.snowflake;

import java.util.concurrent.ThreadLocalRandom;

public final class Snowflake {

	// 비트 구성
	private static final int UNUSED = 1;
	private static final int TIMESTAMP_BITS = 41;
	private static final int NODE_BITS = 10;
	private static final int SEQ_BITS = 12;

	// 각 필드의 최대값
	private static final long NODE_MAX = (1L << NODE_BITS) - 1;
	private static final long SEQ_MAX = (1L << SEQ_BITS) - 1;

	// 기준 시각 (UTC 2024-01-01T00:00:00Z)
	private static final long EPOCH_START = 1704067200000L;

	// 노드 식별자
	private final long nodeKey;

	// 상태 값
	private long lastTimestamp = EPOCH_START;
	private long sequenceValue = 0L;

	public Snowflake() {
		this.nodeKey = ThreadLocalRandom.current().nextLong(0, NODE_MAX + 1);
	}

	public synchronized long nextId() {
		long now = System.currentTimeMillis();

		if (now < lastTimestamp) {
			throw new IllegalStateException("System clock moved backwards");
		}

		if (now == lastTimestamp) {
			sequenceValue = (sequenceValue + 1) & SEQ_MAX;
			if (sequenceValue == 0L) {
				now = waitForNextTick(now);
			}
		} else {
			sequenceValue = 0L;
		}

		lastTimestamp = now;

		return ((now - EPOCH_START) << (NODE_BITS + SEQ_BITS))
				| (nodeKey << SEQ_BITS)
				| sequenceValue;
	}

	private long waitForNextTick(long currentTime) {
		long timestamp = currentTime;
		while (timestamp <= lastTimestamp) {
			timestamp = System.currentTimeMillis();
		}
		return timestamp;
	}
}