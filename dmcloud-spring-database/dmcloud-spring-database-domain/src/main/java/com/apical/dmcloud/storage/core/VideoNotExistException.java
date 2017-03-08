package com.apical.dmcloud.storage.core;

public class VideoNotExistException extends StorageException {

	private static final long serialVersionUID = 1L;
	
	public VideoNotExistException() {
	}

	public VideoNotExistException(String message) {
		super(message);
	}

	public VideoNotExistException(Throwable cause) {
		super(cause);
	}

	public VideoNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

}
