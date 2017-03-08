package com.apical.dmcloud.alarm.middle.core;

public class AlarmException extends Exception {
	private static final long serialVersionUID = 1L;

	public AlarmException() {
	}

	public AlarmException(String message) {
		super(message);
	}

	public AlarmException(Throwable cause) {
		super(cause);
	}

	public AlarmException(String message, Throwable cause) {
		super(message, cause);
	}
}
