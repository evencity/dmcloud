package com.apical.dmcloud.vehicle.core;

public class SIMCardException extends Exception{

	private static final long serialVersionUID = 1L;

	public SIMCardException(){
		
	}
	public SIMCardException(String message) {
		super(message);
	}

	public SIMCardException(Throwable cause) {
		super(cause);
	}

	public SIMCardException(String message, Throwable cause) {
		super(message, cause);
	}
}
