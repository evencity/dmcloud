package com.apical.dmcloud.alarm.middle.core;

public class AlarmTypeNotExistException extends AlarmException {

	private static final long serialVersionUID = 1L;
	
	public AlarmTypeNotExistException() {
		super();
	}

	public AlarmTypeNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlarmTypeNotExistException(String message) {
		super(message);
	}

	public AlarmTypeNotExistException(Throwable cause) {
		super(cause);
	}
}
