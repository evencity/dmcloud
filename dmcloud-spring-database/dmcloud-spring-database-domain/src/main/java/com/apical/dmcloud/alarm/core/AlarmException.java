package com.apical.dmcloud.alarm.core;

public class AlarmException extends Exception {

	private static final long serialVersionUID = 6049003967643669995L;
	
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
