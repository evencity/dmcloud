package com.apical.dmcloud.vehicle.middle.core;

public class SIMCardNotExistException extends VehicleException{
	
	private static final long serialVersionUID = 1L;

	public SIMCardNotExistException(){
		
	}
	public SIMCardNotExistException(String message) {
		super(message);
	}

	public SIMCardNotExistException(Throwable cause) {
		super(cause);
	}

	public SIMCardNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
