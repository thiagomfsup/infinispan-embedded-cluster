package com.tmf.controller;

import static com.tmf.configuration.InfinispanConfiguration.TEST_CACHE_NAME;

import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

	private static final String HELLO_MESSAGE_KEY = "hello.message";

	@Autowired
	private EmbeddedCacheManager ecm;

	@GetMapping
	public ResponseEntity<String> sayHello() {
		String message = (String) ecm.getCache(TEST_CACHE_NAME).get(HELLO_MESSAGE_KEY);

		return ResponseEntity.ok(message == null ? "(Static) Hello!" : message);
	}

	@PostMapping
	public ResponseEntity<?> registerHelloMessage(@RequestBody String message) {
		ecm.getCache(TEST_CACHE_NAME).put(HELLO_MESSAGE_KEY, message);

		return ResponseEntity.noContent().build();
	}
}
