package com.apical.dmcloud.vehicle.core;

public class CompanyIsExistedException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public CompanyIsExistedException(){
		
	}
	public CompanyIsExistedException(String message){
		super(message);
	}
	public CompanyIsExistedException(Throwable cause){
		super(cause);
	}
	public CompanyIsExistedException(String message, Throwable cause){
		super(message, cause);
	}
}
