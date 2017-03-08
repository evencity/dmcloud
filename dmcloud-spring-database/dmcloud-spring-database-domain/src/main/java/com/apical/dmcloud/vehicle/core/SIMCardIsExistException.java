package com.apical.dmcloud.vehicle.core;

public class SIMCardIsExistException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public SIMCardIsExistException(){
		
	}
	public SIMCardIsExistException(String message){
		super(message);
	}
	public SIMCardIsExistException(Throwable cause){
		super(cause);
	}
	public SIMCardIsExistException(String message, Throwable cause){
		super(message, cause);
	}
}
