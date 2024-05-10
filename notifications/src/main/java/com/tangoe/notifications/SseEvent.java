package com.tangoe.notifications;



public class SseEvent {
	private final int userId;

	private final boolean isUpdated;

	private final String message;

	private final EventType type;

	public SseEvent(int userId, boolean isUpdated, String message, EventType type) {
		this.userId = userId;
		this.isUpdated = isUpdated;
		this.message = message;
		this.type = type;

	}

	public int getUserId() {
		return userId;
	}
	public boolean isUpdated() {
		return isUpdated;
	}

	public String getMessage() {
		return message;
	}

	public EventType getType() {
		return type;
	}
}
