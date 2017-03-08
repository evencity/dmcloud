package com.apical.dmcloud.storage.middle.core;

public class StorageException extends Exception {

	private static final long serialVersionUID = 2631317949499926794L;

	public StorageException() {
	}

	public StorageException(String message) {
		super(message);
	}

	public StorageException(Throwable cause) {
		super(cause);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
