package me.jwjung.csms.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.jwjung.csms.authorization.BlackList;
import me.jwjung.csms.authorization.BlackListService;

@RestController
@RequestMapping("/black-list")
@RequiredArgsConstructor
public class BlackListController {

	private final BlackListService service;

	@GetMapping
	public List<BlackList> findAll() {
		return service.findAll();
	}
}
