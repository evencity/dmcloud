package com.apical.dmcloud.rule.middle.core;

public class RuleException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RuleException() {
	}

	public RuleException(String message) {
		super(message);
	}

	public RuleException(Throwable cause) {
		super(cause);
	}

	public RuleException(String message, Throwable cause) {
		super(message, cause);
	}

}
