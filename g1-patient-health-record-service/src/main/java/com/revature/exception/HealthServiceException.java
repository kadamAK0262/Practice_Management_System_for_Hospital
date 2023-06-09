package com.revature.exception;

public class HealthServiceException extends Exception {
	private static final long serialVersionUID = 526706541554094375L;

	public HealthServiceException(String message) {
		super(message);
	}

	public HealthServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public HealthServiceException(Throwable cause) {
		super(cause);
	}

}
