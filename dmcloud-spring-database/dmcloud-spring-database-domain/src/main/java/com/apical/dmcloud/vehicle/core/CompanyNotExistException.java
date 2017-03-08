package com.apical.dmcloud.vehicle.core;

public class CompanyNotExistException extends CompanyException{
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
