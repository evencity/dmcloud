package com.apical.dmcloud.vehicle.core;

public class DriverNotExistException extends DriverException{
	
	private static final long serialVersionUID = 1L;

	public DriverNotExistException(){
		
	}
	public DriverNotExistException(String message) {
		super(message);
	}

	public DriverNotExistException(Throwable cause) {
		super(cause);
	}

	public DriverNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
