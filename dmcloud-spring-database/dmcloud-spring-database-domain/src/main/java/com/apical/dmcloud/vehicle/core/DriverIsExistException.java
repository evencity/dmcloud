package com.apical.dmcloud.vehicle.core;

public class DriverIsExistException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public DriverIsExistException(){
		
	}
	public DriverIsExistException(String message){
		super(message);
	}
	public DriverIsExistException(Throwable cause){
		super(cause);
	}
	public DriverIsExistException(String message, Throwable cause){
		super(message, cause);
	}
}
