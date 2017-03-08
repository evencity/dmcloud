package com.apical.dmcloud.vehicle.core;

public class VehicleNotExistException extends VehicleException{
	private static final long serialVersionUID = 1L;

	public VehicleNotExistException(){
		
	}
	public VehicleNotExistException(String message) {
		super(message);
	}

	public VehicleNotExistException(Throwable cause) {
		super(cause);
	}

	public VehicleNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
