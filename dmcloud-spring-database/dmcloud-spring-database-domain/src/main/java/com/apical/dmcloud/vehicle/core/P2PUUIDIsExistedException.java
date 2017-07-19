package com.apical.dmcloud.vehicle.core;

public class P2PUUIDIsExistedException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public P2PUUIDIsExistedException(){
		
	}
	public P2PUUIDIsExistedException(String message){
		super(message);
	}
	public P2PUUIDIsExistedException(Throwable cause){
		super(cause);
	}
	public P2PUUIDIsExistedException(String message, Throwable cause){
		super(message, cause);
	}
}
