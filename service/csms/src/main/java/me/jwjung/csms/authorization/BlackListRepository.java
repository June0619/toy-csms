package me.jwjung.csms.authorization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BlackListRepository {
	final private static Map<Long, BlackList> blackListMap = new HashMap<>();
	private static Long SEQUENCE = 0L;


	public void save(BlackList blackList) {
		blackListMap.put(++SEQUENCE, blackList);
		blackList.setId(SEQUENCE);
	}

	public List<BlackList> findAll() {
		return blackListMap.values().stream().toList();
	}

}
