package me.jwjung.csms.authorization;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlackListService {
	private final BlackListRepository blackListRepository;

	@Transactional
	public void save(String memberUuid) {
		blackListRepository.save(new BlackList(memberUuid));
		log.info("blacklist added : {}", memberUuid);
	}

	public List<BlackList> findAll() {
		return blackListRepository.findAll();
	}
}
