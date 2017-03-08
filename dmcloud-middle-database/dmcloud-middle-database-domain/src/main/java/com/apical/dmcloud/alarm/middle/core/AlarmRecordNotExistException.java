package com.apical.dmcloud.alarm.middle.core;

public class AlarmRecordNotExistException extends AlarmException {

	private static final long serialVersionUID = -668444874223965078L;
	
	public AlarmRecordNotExistException() {
		super();
	}

	public AlarmRecordNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlarmRecordNotExistException(String message) {
		super(message);
	}

	public AlarmRecordNotExistException(Throwable cause) {
		super(cause);
	}

}
