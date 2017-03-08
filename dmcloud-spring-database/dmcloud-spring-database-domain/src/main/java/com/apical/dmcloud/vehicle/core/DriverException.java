package com.apical.dmcloud.vehicle.core;

public class DriverException extends Exception{
	private static final long serialVersionUID = 1L;

	public DriverException(){
		
	}
	public DriverException(String message) {
		super(message);
	}

	public DriverException(Throwable cause) {
		super(cause);
	}

	public DriverException(String message, Throwable cause) {
		super(message, cause);
	}
}
