package com.bridgelabz.userservice.utilservice.exceptions;

/**
 * @author yuga
 * @since 06/08/2018
 * <p>
 * <b>To throw custom exceptions occur at execution time</b>
 * </p>
 */

public class ToDoExceptions extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * <p><b>This Constructor call super constructor with an argument String type of message </b></p>
	 */
	
	public ToDoExceptions(String message) {
		super(message);
	}
	
	
}