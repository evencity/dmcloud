package com.apical.dmcloud.security.core;

public class SecurityRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2631317949499926794L;

	public SecurityRuntimeException() {
	}

	public SecurityRuntimeException(String message) {
		super(message);
	}

	public SecurityRuntimeException(Throwable cause) {
		super(cause);
	}

	public SecurityRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
