package com.apical.dmcloud.vehicle.core;

public class DeviceNotExistException extends DeviceException{
	
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
