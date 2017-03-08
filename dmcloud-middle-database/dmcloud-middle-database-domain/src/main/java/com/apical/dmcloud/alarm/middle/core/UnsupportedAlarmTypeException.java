package com.apical.dmcloud.alarm.middle.core;

public class UnsupportedAlarmTypeException extends AlarmException {

	private static final long serialVersionUID = 1L;
	
	public UnsupportedAlarmTypeException() {
		super();
	}

	public UnsupportedAlarmTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedAlarmTypeException(String message) {
		super(message);
	}

	public UnsupportedAlarmTypeException(Throwable cause) {
		super(cause);
	}
}
