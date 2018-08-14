package com.bridgelabz.userservice.model;

import java.io.Serializable;

/**
 * @author yuga
 * @since 06/08/2018
 * <p><b>To provide setter and getter methods to deal with registration details</b>
 * </p>
 */
public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private String confirmPassword;
	private String firstName;
	private String lastName;
	private String mobile;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
