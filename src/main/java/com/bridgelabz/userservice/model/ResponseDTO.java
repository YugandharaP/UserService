package com.bridgelabz.userservice.model;

import java.io.Serializable;

/**
 * @author yuga
 * @since 13/07/2018
 *<p>
 *<b>This is Response DTO(Data transfer object)class which deals with
 *web and controller to taking messages and sending back </b>
 *</p>
 *
 */
public class ResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;
	
	public ResponseDTO(String message,int status){
		this.status=status;
		this.message=message;
	}

	public ResponseDTO() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

