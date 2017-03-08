package com.apical.dmcloud.vehicle.core;

public class DeviceIsExistException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public DeviceIsExistException(){
		
	}
	public DeviceIsExistException(String message){
		super(message);
	}
	public DeviceIsExistException(Throwable cause){
		super(cause);
	}
	public DeviceIsExistException(String message, Throwable cause){
		super(message, cause);
	}
}
