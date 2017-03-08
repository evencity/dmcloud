package com.apical.dmcloud.security.core;

public class CopyOfNameIsExistedException extends SecurityRuntimeException {

	private static final long serialVersionUID = 5085396915916927288L;

	public CopyOfNameIsExistedException() {
		super();
	}

	public CopyOfNameIsExistedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CopyOfNameIsExistedException(String message) {
		super(message);
	}

	public CopyOfNameIsExistedException(Throwable cause) {
		super(cause);
	}

}
