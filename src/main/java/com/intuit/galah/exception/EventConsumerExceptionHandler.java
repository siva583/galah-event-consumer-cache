package com.intuit.galah.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.intuit.galah.util.ServiceUtil;

@ControllerAdvice
public class EventConsumerExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@ExceptionHandler(value = {Exception.class})
	@ResponseBody
	public ResponseEntity<?> handleAnyException(Exception e) {
		return serviceUtil.createResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
