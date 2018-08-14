package com.bridgelabz.userservice.utilservice.exceptions;

import com.google.common.annotations.GwtCompatible;

/**
 * @author yuga
 * @since 06/08/2018
 *
 */
@GwtCompatible
public class RestPreconditions{
	private RestPreconditions() {
	}

	/**
	 * Ensures that the object given as a method parameter is not null.
	 * 
	 * @param reference A reference to the inspected object.
	 * @param errorMessage The error message that is passed forward to the exception that is thrown if the object given as a method parameter is null.
	 * @throws ToDoExceptions If the object given as a method parameter is null.
	 */

	public static <T> T checkNotNull(T reference, String errormessage) throws ToDoExceptions {
		if (reference == null) {
			throw new ToDoExceptions(errormessage);
		}
		return reference;
	}
	
	/**
	   * Ensures the truth of an expression involving one or more parameters to the calling method.
	   *
	   * @param expression a boolean expression
	   * @param errorMessage the exception message to use if the check fails;
	   * @throws ToDoExceptions 
	   */
	  public static void checkArgument(boolean expression,String errormessage) throws ToDoExceptions {
	    if (expression) {
	      throw new ToDoExceptions(errormessage);
	    }
	  }


}