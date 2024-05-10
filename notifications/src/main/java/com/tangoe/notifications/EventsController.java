package com.tangoe.notifications;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin
public class EventsController {

	public EventsController(EventsService eventsService) {
		this.eventsService = eventsService;
	}

	private final EventsService eventsService;

	@GetMapping("/events")
	public SseEmitter eventStream(@RequestParam int userId) {
		return eventsService.startProcessing(userId);
	}

	@GetMapping("/ops")
	public int operation1() {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return 100;

	}
}
