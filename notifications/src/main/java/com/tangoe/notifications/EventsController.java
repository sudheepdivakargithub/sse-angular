package com.tangoe.notifications;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@CrossOrigin
public class EventsController {

	public EventsController(EventsService eventsService) {
		this.eventsService = eventsService;
	}

	private final EventsService eventsService;

	/**
	 * This endpoint helps clients for listening to the server-sent events.
	 * @return SseEmitter
	 */
	@GetMapping("/events")
	public SseEmitter eventStream() {
		return eventsService.startProcessing();
	}

	/**
	 * This endpoint is to mimic a long-running operation
	 * @return Num
	 */
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
