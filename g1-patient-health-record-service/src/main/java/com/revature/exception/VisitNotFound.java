package com.revature.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class VisitNotFound extends RuntimeException {
	private String message;

	public VisitNotFound(String message) {
		super(message);
		this.message = message;
	}
}
