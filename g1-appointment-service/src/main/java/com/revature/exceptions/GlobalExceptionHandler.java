package com.revature.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
//	private static final Logger log = LogManager.getLogger(AllergyService.class);
	
	 @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exp, WebRequest request) {
        
         List<String> details = new ArrayList<String>();
         details.add(exp.getMessage());
         
         System.out.println("ResourceNotFoundException executed");
         ApiError error = new ApiError(LocalDateTime.now(),
        		 HttpStatus.NOT_FOUND
        		 ,exp.getMessage(),
        		 request.getDescription(false));
//         log.warn(PAGE_NOT_FOUND_LOG_CATEGORY);
         return ResponseEntityHelper.build(error);
     }
	 
	 
	 @ExceptionHandler(MethodArgumentTypeMismatchException.class)
     protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
         MethodArgumentTypeMismatchException ex,
         WebRequest request) {
         
         List<String> details = new ArrayList<String>();
         details.add(ex.getMessage());
       
         System.out.println("MethodArgumentTypeMismatchException executed");
         ApiError err = new ApiError(
             LocalDateTime.now(),
             HttpStatus.BAD_REQUEST, 
             ex.getMessage(),
             request.getDescription(false));
//         log.warn(PAGE_NOT_FOUND_LOG_CATEGORY);
         return ResponseEntityHelper.build(err);
     }	 
	 
	 @ExceptionHandler({ Exception.class })
     public ResponseEntity<Object> handleAll(
         Exception ex, 
         WebRequest request) {
         
         List<String> details = new ArrayList<String>();
         details.add(ex.getLocalizedMessage());
         
         System.out.println("exception class executed");
         
         ApiError err = new ApiError(
             LocalDateTime.now(),
             HttpStatus.BAD_REQUEST,
             ex.getMessage(),
             request.getDescription(false)
             );
         
         return ResponseEntityHelper.build(err);
     }
	
	


}
