package me.jwjung.repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjung.service.FeeService;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SessionRepository {
	private final StringRedisTemplate redisTemplate;
	private final FeeService feeService;
	private static final Duration TTL = Duration.ofHours(12);

	/** StartTransaction 시: 세션 생성 */
	public void createSession(String transactionId) {
		String key = generateKey(transactionId);

		redisTemplate.opsForHash().put(key, "totalChargingAmount", "0");
		redisTemplate.opsForHash().put(key, "totalFee", "0");
		redisTemplate.opsForHash().put(key, "lastUpdatedAt", LocalDateTime.now().toString());
		redisTemplate.expire(key, TTL);

		log.info("[SessionRepository.createSession] key={}", key);
	}

	/** MeterValueChanged 시: 충전량 누적 및 요금 갱신 */
	public void updateMeterValue(String transactionId, Double newMeterValue) {
		String key = generateKey(transactionId);

		Map<Object, Object> session = redisTemplate.opsForHash().entries(key);
		if (session.isEmpty()) {
			return;
		}

		Double oldMeterValue = Double.valueOf(session.getOrDefault("meterReading", "0").toString());
		Double diff = Math.max(0, newMeterValue - oldMeterValue);

		Double totalEnergy = Double.valueOf(session.getOrDefault("totalChargingAmount", "0").toString()) + diff;

		// 외부 FeeService 로 요금 계산 (Redis 접근 금지)
		Integer billingFee = feeService.calculateFee(transactionId, totalEnergy);

		// Redis 에 누적 반영
		redisTemplate.opsForHash().put(key, "meterReading", newMeterValue.toString());
		redisTemplate.opsForHash().put(key, "totalChargingAmount", totalEnergy.toString());
		redisTemplate.opsForHash().put(key, "totalFee", billingFee.toString());
		redisTemplate.opsForHash().put(key, "lastUpdatedAt", LocalDateTime.now().toString());
		redisTemplate.expire(key, TTL);

		log.info("Session updated: key={}, ΔkWh={}, totalEnergy={}, totalFee={}원",
					key, diff, totalEnergy, billingFee);
	}

	/** StopTransaction 시: 세션 데이터 반환 후 삭제 */
	public Map<Object, Object> stopSession(String transactionId) {
		String key = generateKey(transactionId);
		Map<Object, Object> session = redisTemplate.opsForHash().entries(key);

		if (session.isEmpty()) {
			return Map.of();
		}

		Double totalEnergy = Double.valueOf(session.getOrDefault("totalChargingAmount", "0").toString());
		Integer billingFee = feeService.calculateFee(transactionId, totalEnergy);

		// 최종 요금 보정 후 반환 (Redis 삭제)
		redisTemplate.delete(key);

		log.info("Session stopped: tx={}, totalEnergy={}kWh, finalFee={}원", transactionId, totalEnergy, billingFee);
		return Map.of(
				"transactionId", transactionId,
				"totalChargingAmount", totalEnergy,
				"totalFee", billingFee
		);
	}

	// charging::session::%s
	private static final String KEY_FORMAT = "charging::session::%s";

	private String generateKey(String transactionId) {
		return KEY_FORMAT.formatted(transactionId);
	}
}
