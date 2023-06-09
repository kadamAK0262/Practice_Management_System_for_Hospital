package com.revature.exception;

import org.springframework.http.ResponseEntity;

public class ResponseEntityHelper {
	
	public static ResponseEntity<Object> build(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());

}

}
