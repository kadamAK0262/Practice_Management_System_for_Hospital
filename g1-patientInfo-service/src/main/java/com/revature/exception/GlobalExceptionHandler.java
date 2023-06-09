package com.revature.exception;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	 @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exp, WebRequest request) {
        
         List<String> details = new ArrayList<String>();
         details.add(exp.getMessage());
         
         ApiError error = new ApiError(LocalDateTime.now(),
        		 HttpStatus.NOT_FOUND
        		 ,exp.getMessage(),
        		 request.getDescription(false));
         System.out.println("res not patient found exception executed");
         return ResponseEntityHelper.build(error);
     }
	 @ExceptionHandler(MethodArgumentTypeMismatchException.class)
     protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
         MethodArgumentTypeMismatchException ex,
         WebRequest request) {
         
         List<String> details = new ArrayList<String>();
         details.add(ex.getMessage());
       
         ApiError err = new ApiError(
             LocalDateTime.now(),
             HttpStatus.BAD_REQUEST, 
             ex.getMessage(),
             request.getDescription(false));
         System.out.println("method not patient found exception executed");
         return ResponseEntityHelper.build(err);
     }
	 
	 @ExceptionHandler(NoSuchElementException.class)
     protected ResponseEntity<Object> handleNoSuchElementFoundException(
    		 NoSuchElementException ex,
         WebRequest request) {
         
         List<String> details = new ArrayList<String>();
         details.add(ex.getMessage());
       
         ApiError err = new ApiError(
             LocalDateTime.now(),
             HttpStatus.BAD_REQUEST, 
             ex.getMessage(),
             request.getDescription(false));
         System.out.println("no such element not patient found exception executed");
         return ResponseEntityHelper.build(err);
     }
	 
	 @ExceptionHandler( value = {Exception.class} )
     public ResponseEntity<Object> handleAll(
         Exception ex, 
         WebRequest request) {
         
         List<String> details = new ArrayList<String>();
         details.add(ex.getLocalizedMessage());
         
         ApiError err = new ApiError(
             LocalDateTime.now(),
             HttpStatus.BAD_REQUEST,
             "no data",
             request.getDescription(false)
             );
         System.out.println("exception class not patient found exception executed");
         return ResponseEntityHelper.build(err);
     }
}

