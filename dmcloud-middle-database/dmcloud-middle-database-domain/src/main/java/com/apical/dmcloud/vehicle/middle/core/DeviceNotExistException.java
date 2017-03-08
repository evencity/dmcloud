package com.apical.dmcloud.vehicle.middle.core;

public class DeviceNotExistException extends VehicleException{
	
	private static final long serialVersionUID = 1L;
	public DeviceNotExistException(){
		
	}
	public DeviceNotExistException(String message){
		super(message);
	}
	public DeviceNotExistException(Throwable cause){
		super(cause);
	}
	public DeviceNotExistException(String message, Throwable cause){
		super(message, cause);
	}
}
