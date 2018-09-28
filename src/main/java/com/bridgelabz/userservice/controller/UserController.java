package com.bridgelabz.userservice.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.bridgelabz.userservice.model.ResponseDTO;
import com.bridgelabz.userservice.model.User;
import com.bridgelabz.userservice.model.UserDTO;
import com.bridgelabz.userservice.service.IFeignClientService;
import com.bridgelabz.userservice.service.IUserService;
import com.bridgelabz.userservice.utilservice.exceptions.ToDoExceptions;
import com.bridgelabz.userservice.utilservice.messageservice.MessageSourceService;
import com.bridgelabz.userservice.utilservice.redisservice.IRedisRepository;



/**
 * @author yuga
 * @since 06/08/2018
 * <p><b>To interact with the view and services.controller is the media
 * between view and model.</b></p>
 */
@RestController
@RefreshScope
@RequestMapping("/user")
public class UserController {

	final static String REQUEST_ID ="IN_USER";
	final static String RESPONSE_ID="OUT_USER";
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService ;
	
	@Autowired
	private IRedisRepository redisRepository;
	
	@Autowired
	private IFeignClientService fiegnClientService;
	
	@Value("${innerFolderName}")
	String innerFolderName;
	
	/**<p><b>To take register url from view and perform operations</b></p>
	 * @param registerdto
	 * @return response
	 * @throws ToDoExceptions 
	 * @throws RegistrationExceptions
	 * @throws MessagingException
	 */
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userdto ) throws ToDoExceptions, MessagingException {
		logger.info(REQUEST_ID);
		
		userService.register(userdto);
		logger.info(RESPONSE_ID);
		return new ResponseEntity<>(MessageSourceService.getMessage("100")+userdto.getEmail(),HttpStatus.OK);
	}
	/**<p><b>To take activation url from view and perform operations</b></p>
	 * @param token
	 * @return response
	 * @throws ToDoExceptions 
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/useractivation")
	public ResponseEntity<ResponseDTO> activateUser(HttpServletRequest request ) throws ToDoExceptions {
		logger.info(REQUEST_ID);
		String userId= request.getHeader("userId");
		userService.activateUser(userId);
		logger.info(RESPONSE_ID);
		return new ResponseEntity(MessageSourceService.getMessage("101"),HttpStatus.OK);
	}

	/**
	 * @param logindto
	 * <p><b>To take login url from view and perform operations</b></p>
	 * @return response
	 * @throws Exception 
	 * @throws ToDoExceptions 
	 * @throws LoginException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> loginUser(@RequestBody UserDTO userdto,HttpServletResponse resp ) throws ToDoExceptions, Exception  {
			logger.info(REQUEST_ID);
			logger.debug("in debug mode");
			String token=userService.login(userdto);
			logger.info("in user Login : "+token);
			redisRepository.setToken(token);
			logger.info("redis work");
			resp.setHeader("token",token);
			logger.info(RESPONSE_ID);
			return new ResponseEntity(MessageSourceService.getMessage("102"),HttpStatus.OK);

	}
	/**
	 * @param String emailId
	 * <p><b>To take forgot password url from view and perform operations</b></p>
	 * @return response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/forgetpassword/{email}")
	public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable (value="email") String email,HttpServletRequest request ) throws Exception {
			logger.info(REQUEST_ID);
			String token= request.getHeader("token");
			userService.forgotPassword(email,token);
			logger.info(RESPONSE_ID);
			return new ResponseEntity(MessageSourceService.getMessage("117"),HttpStatus.OK);
	}
	
	/**
	 * @param token
	 * @param userdto
	 * <p><b>To take reset password url from view and perform operations </b></p>
	 * @return response
	 * @throws ToDoExceptions
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/resetpassword")
	public ResponseEntity<ResponseDTO> resetpassword(HttpServletRequest request ,@RequestBody UserDTO userdto) throws ToDoExceptions 
	{
			logger.info(REQUEST_ID);
			String token = request.getHeader("token");
			userService.resetPassword(token,userdto);
			logger.info(RESPONSE_ID);
			return new ResponseEntity(MessageSourceService.getMessage("104"),HttpStatus.OK);
	}
	
	/**
	 * @param emailId
	 * <p><b>To take getuserbyemailid and pathvariable from view and perform operations </b></p>
	 * @return User details
	 * @throws ToDoExceptions
	 */
	@GetMapping("/getuserbyemailid/{emailId}")
	public ResponseEntity<?>getUserByEmailId(@PathVariable ("emailId")String emailId) throws ToDoExceptions{
		logger.info(REQUEST_ID);
		User user = userService.getUserByEmailId(emailId);
		logger.info(RESPONSE_ID);
		return  new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	/**<p><b>To take getallusers url from view and perform operations </b></p>
	 * @return list Of users
	 * @throws ToDoExceptions 
	 */
	@GetMapping("/getallusers")
	public ResponseEntity<?>getAllUsers() throws ToDoExceptions{
		logger.info(REQUEST_ID);
		List<?> userList = userService.getAllUser();
		logger.info(RESPONSE_ID);
		return  new ResponseEntity<>(userList,HttpStatus.OK);
	}
	
	/**
	 * @param token
	 * @param folderName
	 * @param imageFile
	 * @param innerFolderName
	 * <p><b></b></p>
	 * @return response to the view
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws IOException
	 * @throws ToDoExceptions
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/uploadimage")
	public ResponseEntity<?> uploadImage(@RequestHeader(value="token")String token,
										@RequestParam MultipartFile imageFile,HttpServletRequest request) 
										throws AmazonServiceException, AmazonClientException, IOException, ToDoExceptions {
		
		String filePath=userService.convertMultipartFileToJavaFile(imageFile);
		String userId = request.getHeader("userId");
		String imageUrl =  fiegnClientService.uploadImage(userId, filePath,innerFolderName);
		userService.setProfilePicture(userId, imageUrl);
		return new ResponseEntity(MessageSourceService.getMessage("120"),HttpStatus.OK);
	}
}
