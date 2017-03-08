package com.apical.dmcloud.alarm.core;

public class NameIsExistedException extends AlarmRuntimeException {

	private static final long serialVersionUID = -668444874223965078L;
	
	public NameIsExistedException() {
		super();
	}

	public NameIsExistedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NameIsExistedException(String message) {
		super(message);
	}

	public NameIsExistedException(Throwable cause) {
		super(cause);
	}

}
