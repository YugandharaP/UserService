package com.bridgelabz.userservice.model;
import java.net.URL;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * @author yuga
 * @since 06/08/2018
 *<p><b>To provide setter and getter methods to deal with user details</b></p>
 */
@Document
public class User {

	@Id
	private String id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String mobile;
	private List<URL>imageList;
	private boolean status;

	public User() {

	}


	public String getId() {
		return id;
	}

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

	public List<URL> getImageList() {
		return imageList;
	}


	public void setImageList(List<URL> imageList) {
		this.imageList = imageList;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}