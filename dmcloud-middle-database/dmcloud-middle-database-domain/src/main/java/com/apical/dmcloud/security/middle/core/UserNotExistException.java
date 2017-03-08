package com.apical.dmcloud.security.middle.core;

public class UserNotExistException extends SecurityException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotExistException() {
		super();
	}

	public UserNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotExistException(String message) {
		super(message);
	}

	public UserNotExistException(Throwable cause) {
		super(cause);
	}
}
