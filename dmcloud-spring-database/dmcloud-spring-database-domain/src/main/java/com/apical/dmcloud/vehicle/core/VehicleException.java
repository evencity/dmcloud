package com.apical.dmcloud.vehicle.core;

public class VehicleException extends Exception{
	private static final long serialVersionUID = 1L;

	public VehicleException(){
		
	}
	public VehicleException(String message) {
		super(message);
	}

	public VehicleException(Throwable cause) {
		super(cause);
	}

	public VehicleException(String message, Throwable cause) {
		super(message, cause);
	}
}
