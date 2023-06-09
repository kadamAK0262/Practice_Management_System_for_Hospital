package com.revature.exceptions;

public class MethodArgumentTypeMismatchException extends RuntimeException{

	private static final long serialVersionUID = 1L;

    public MethodArgumentTypeMismatchException(String message) {
        super(message);
    }
}
