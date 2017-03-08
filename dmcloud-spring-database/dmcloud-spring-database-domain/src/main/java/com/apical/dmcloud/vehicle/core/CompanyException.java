package com.apical.dmcloud.vehicle.core;

public class CompanyException extends Exception{

	private static final long serialVersionUID = 1L;

	public CompanyException(){
		
	}
	public CompanyException(String message) {
		super(message);
	}

	public CompanyException(Throwable cause) {
		super(cause);
	}

	public CompanyException(String message, Throwable cause) {
		super(message, cause);
	}

}
