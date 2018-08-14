package com.bridgelabz.userservice.utilservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.userservice.model.ResponseDTO;


/**
 *@author yuga
 *@since 13/07/2018
 *<p><b>This class can handle all type of exceptions which come into controller</b></p> */
@ControllerAdvice
public class ToDoExceptionHandler {
	
	/**
	 * @param exception
	 * @return response with Http status
	 * <p><b>To handle generic exceptions </b></p>	
	 **/
	@ExceptionHandler(ToDoExceptions.class)
	public ResponseEntity<ResponseDTO> todoExceptionHandler(ToDoExceptions exception) {
		ResponseDTO response = new ResponseDTO();
		response.setMessage(exception.getMessage());
		response.setStatus(-1);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
	}
	/**
	 * @param exception
	 * @return response with Http status
	 * <p><b>To handle generic exceptions </b></p>	
	 **/
	/*@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> genericExceptionHandler(Exception exception) {
		ResponseDTO response = new ResponseDTO();
		response.setMessage("something went wrong!");
		response.setStatus(-1);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
}

