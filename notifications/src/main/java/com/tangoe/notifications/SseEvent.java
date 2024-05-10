package com.tangoe.notifications;

public class SseEvent {
	private final int usersProcessed;

	private final boolean finished;

	public SseEvent(int usersProcessed, boolean finished) {
		this.usersProcessed = usersProcessed;
		this.finished = finished;
	}

	public int getUsersProcessed() {
		return usersProcessed;
	}

	public boolean isFinished() {
		return finished;
	}
}
