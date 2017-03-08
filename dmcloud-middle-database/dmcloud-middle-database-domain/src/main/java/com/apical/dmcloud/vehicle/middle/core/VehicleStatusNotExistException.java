package com.apical.dmcloud.vehicle.middle.core;

public class VehicleStatusNotExistException extends VehicleException{
	private static final long serialVersionUID = 1L;

	public VehicleStatusNotExistException(){
		
	}
	public VehicleStatusNotExistException(String message) {
		super(message);
	}

	public VehicleStatusNotExistException(Throwable cause) {
		super(cause);
	}

	public VehicleStatusNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
