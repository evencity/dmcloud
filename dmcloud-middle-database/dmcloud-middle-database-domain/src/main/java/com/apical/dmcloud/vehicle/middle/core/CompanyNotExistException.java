package com.apical.dmcloud.vehicle.middle.core;

public class CompanyNotExistException extends VehicleException{
	private static final long serialVersionUID = 1L;
	public CompanyNotExistException(){
		
	}
	public CompanyNotExistException(String message){
		super(message);
	}
	public CompanyNotExistException(Throwable cause){
		super(cause);
	}
	public CompanyNotExistException(String message, Throwable cause){
		super(message, cause);
	}
}
