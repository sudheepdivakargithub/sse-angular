package com.tangoe.notifications;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EventsService {

	private final ConcurrentMap<Integer, SseEmitter> ongoingProcess = new ConcurrentHashMap<>();

	public SseEmitter startProcessing(int userId)  {

//		if (ongoingProcess.containsKey(userId)) {
//			return ongoingProcess.get(userId);
//		}
		SseEmitter emitter = new SseEmitter();
//		ongoingProcess.put(userId, emitter);
		ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
		sseMvcExecutor.execute(() -> {
			sendEvent(emitter, userId);
		});
		return emitter;
	}

	void sendEvent(SseEmitter emitter, int userId) {
		try {

			for (int counter = 0; counter < 10; counter++) {
				SseEmitter.SseEventBuilder event = SseEmitter.event().id(String.valueOf(counter)).name("NOTIFICATION")
						.data(new SseEvent(counter, false));
				emitter.send(event);
				Thread.sleep(1000);
			}
			SseEmitter.SseEventBuilder event = SseEmitter.event().id(String.valueOf(-1)).name("COMPLETE")
					.data(new SseEvent(-1, true));
			emitter.send(event);
			emitter.complete();

		} catch (IOException | InterruptedException e) {
			emitter.completeWithError(e);
		} finally {
//			ongoingProcess.remove(userId);
		}
	}

}
