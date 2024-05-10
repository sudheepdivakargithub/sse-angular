package com.tangoe.notifications;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EventsService {

	//Initializing executor with an Optimal Thread Pool Size.
	private final ExecutorService executor = Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors() / 2);

	/**
	 * This method sets up the SseEmitter and delegates the task to be executed asynchronously.
	 *
	 * @return SseEmitter
	 */
	public SseEmitter startProcessing() {
		SseEmitter emitter = new SseEmitter();
		executor.execute(() -> sendEvent(emitter));
		return emitter;
	}

	/**
	 * This function undergoes 10 iterations. During each iteration, it emits an event and suspends the thread for 1
	 * second to simulate a prolonged operation. Post iteration, it dispatches a completion event to signal
	 * the client to disconnect, thereby finalizing the emitting process.
	 *
	 * @param emitter Reference to the SseEmitter for publishing events.
	 */
	void sendEvent(SseEmitter emitter) {
		try {
			for (int userCount = 1; userCount <= 10; userCount++) {
				SseEmitter.SseEventBuilder event = SseEmitter.event().id(String.valueOf(userCount)).name("NOTIFICATION")
						.data(new SseEvent(userCount, true,null,EventType.RUNNING));
				emitter.send(event);
				Thread.sleep(1000);
			}
			SseEmitter.SseEventBuilder event = SseEmitter.event().id(String.valueOf(-1)).name("COMPLETE")
					.data(new SseEvent(-1, true,null,EventType.TERMINATED));
			emitter.send(event);
			emitter.complete();
		} catch (IOException | InterruptedException e) {
			emitter.completeWithError(e);
		}
	}

}
