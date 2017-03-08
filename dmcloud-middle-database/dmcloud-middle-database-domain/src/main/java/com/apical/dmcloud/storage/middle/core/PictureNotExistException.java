package com.apical.dmcloud.storage.middle.core;

public class PictureNotExistException extends StorageException {

	private static final long serialVersionUID = 1L;
	
	public PictureNotExistException() {
	}

	public PictureNotExistException(String message) {
		super(message);
	}

	public PictureNotExistException(Throwable cause) {
		super(cause);
	}

	public PictureNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

}
