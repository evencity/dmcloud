package com.apical.dmcloud.rule.core;

public class RuleRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RuleRuntimeException() {
	}

	public RuleRuntimeException(String message) {
		super(message);
	}

	public RuleRuntimeException(Throwable cause) {
		super(cause);
	}

	public RuleRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
