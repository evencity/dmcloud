package com.apical.dmcloud.security.core;

public class UserAccountIsExistedException extends SecurityRuntimeException {

	private static final long serialVersionUID = -7110203072088801962L;

	public UserAccountIsExistedException() {
		super();
	}

	public UserAccountIsExistedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAccountIsExistedException(String message) {
		super(message);
	}

	public UserAccountIsExistedException(Throwable cause) {
		super(cause);
	}

}
