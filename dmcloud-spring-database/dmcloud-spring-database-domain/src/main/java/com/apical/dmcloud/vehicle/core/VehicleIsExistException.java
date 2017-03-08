package com.apical.dmcloud.vehicle.core;

public class VehicleIsExistException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public VehicleIsExistException(){
		
	}
	public VehicleIsExistException(String message){
		super(message);
	}
	public VehicleIsExistException(Throwable cause){
		super(cause);
	}
	public VehicleIsExistException(String message, Throwable cause){
		super(message, cause);
	}
}
