package com.bridgelabz.userservice.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.security.auth.login.LoginException;

import com.bridgelabz.userservice.model.User;
import com.bridgelabz.userservice.model.UserDTO;
import com.bridgelabz.userservice.utilservice.exceptions.ToDoExceptions;


/**
* @author yuga
* @since 09/07/2018
*<p><b>To connect the controller and mongoDb repository.service interface is the media between controller and repository 
*</b></p>
*/
public interface IUserService {

	/**
	 * @param logindto
	 * @return boolean values
	 * @throws LoginException 
	 * <p>to signin user in todo app</P>
	 * @throws ToDoExceptions 
	 * @throws Exception 
	 */
	String login(UserDTO userdto) throws ToDoExceptions, Exception;


	/**
	 * @param logindto
	 * @return boolean values
	 * @throws RegistrationExceptions 
	 * @throws ToDoExceptions
	 *  <p>to signup user in todo app</P>
	 * @throws MessagingException 
	 */
	void register(UserDTO userdto) throws ToDoExceptions, MessagingException;


	/**
	 * @param token
	 *  <p>to activate the user</P>
	 * @throws ToDoExceptions 
	 */
	void activateUser(String token) throws ToDoExceptions;


	/**
	 * @param String emailId
	 * <p>to change password for user of todo application
	 * @throws Exception 
	 */
	void forgotPassword(String emailId) throws Exception;

	/**
	 * @param passworddto,String token
	 * <p>to reset password for user of todo application
	 * @throws ToDoExceptions 
	 * @throws Exception 
	 */
	void resetPassword(String token, UserDTO userdto) throws ToDoExceptions;


	/**
	 * @param userId
	 * <p><b>return user details which is present in database</b></p>
	 * @throws ToDoExceptions 
	 */
	User getUserByEmailId(String emailId) throws ToDoExceptions;


	/**
	 * @return List Of Users
	 * @throws ToDoExceptions 
	 */
	public List<?> getAllUser() throws ToDoExceptions;
	
}
