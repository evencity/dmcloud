package com.apical.dmcloud.alarm.middle.core;

public class AlarmRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6049003967643669995L;
	
	public AlarmRuntimeException() {
	}

	public AlarmRuntimeException(String message) {
		super(message);
	}

	public AlarmRuntimeException(Throwable cause) {
		super(cause);
	}

	public AlarmRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
