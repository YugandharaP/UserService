package com.bridgelabz.userservice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.userservice.model.Email;
import com.bridgelabz.userservice.model.User;
import com.bridgelabz.userservice.model.UserDTO;
import com.bridgelabz.userservice.repository.IUserRepository;
import com.bridgelabz.userservice.utilservice.exceptions.RestPreconditions;
import com.bridgelabz.userservice.utilservice.exceptions.ToDoExceptions;
import com.bridgelabz.userservice.utilservice.mailservice.IEmailService;
import com.bridgelabz.userservice.utilservice.mailservice.IMailProducer;
import com.bridgelabz.userservice.utilservice.messageservice.MessageSourceService;
import com.bridgelabz.userservice.utilservice.modelmapperservice.ModelMapperService;
import com.bridgelabz.userservice.utilservice.redisservice.IRedisRepository;
import com.bridgelabz.userservice.utilservice.securityservice.JwtTokenProvider;
import com.bridgelabz.userservice.utilservice.sqsmailservice.MessageService;



/**
 * @author yuga
 * @since 06/08/2018
 *        <p>
 * 		<b>To connect controller and MongoRepository and provides
 *        implementation of the service methods </b>
 *        </p>
 */
@Service
public class UserServiceImplementation implements IUserService {
	@Autowired
	IUserRepository userRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplementation.class);

	@SuppressWarnings("unused")
	@Autowired
	private IEmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IMailProducer mailProducer;
	
	@Autowired
	private ModelMapperService modelMapper;
	
	@Autowired
	private IRedisRepository redisRepository;
	
	@Autowired
	private MessageService messageService;
	
	
	@Value("${ipaddress}")
	String ipAddress;
	
	/**
	 * @param userdto
	 * <p><b>To check conditon(check email id and password) when user want's to login</b>  </p>
	 * @throws ToDoExceptions
	 * @exception throws ToDoException exception
	 */
	@Override
	public String login(UserDTO userdto) throws ToDoExceptions, Exception {
		RestPreconditions.checkNotNull(userdto.getEmail(), MessageSourceService.getMessage("106"));
		RestPreconditions.checkNotNull(userdto.getPassword(),  MessageSourceService.getMessage("107"));
		RestPreconditions.checkArgument(userdto.getEmail().equals(""), MessageSourceService.getMessage("106"));
		RestPreconditions.checkArgument(userdto.getPassword().equals(""), MessageSourceService.getMessage("107") );
		
		Optional<User> optionalUser = userRepository.findByEmail(userdto.getEmail());
		RestPreconditions.checkArgument(!optionalUser.isPresent(), MessageSourceService.getMessage("111"));
		if (!passwordEncoder.matches(userdto.getPassword(), optionalUser.get().getPassword())) {
			LOGGER.error( MessageSourceService.getMessage("112"));
			throw new ToDoExceptions( MessageSourceService.getMessage("112"));
		}
		RestPreconditions.checkArgument(!optionalUser.get().isStatus(), MessageSourceService.getMessage("113"));

			User user = optionalUser.get();
			JwtTokenProvider token = new JwtTokenProvider();
			String tokenGenerated = token.generator(user);
			LOGGER.info("token : " + tokenGenerated);
			String userId= user.getId();
			String tokenFromRedis = redisRepository.getToken(userId);
			LOGGER.info("token from Redis"+tokenFromRedis);

			RestPreconditions.checkNotNull(tokenFromRedis, MessageSourceService.getMessage("114"));
			LOGGER.error( MessageSourceService.getMessage("114"));
			
			return tokenFromRedis;
	}

	/**
	 * @param registerdto
	 * <p><b>To check whether user is already present in database or not</b></p>
	 * @throws RegistrationExceptions
	 * @throws MessagingException
	 * @throws ToDOException
	 */
	@Override
	public void register(UserDTO userdto) throws ToDoExceptions, MessagingException {
		RestPreconditions.checkNotNull(userdto.getEmail(), MessageSourceService.getMessage("106"));
		RestPreconditions.checkNotNull(userdto.getFirstName(), MessageSourceService.getMessage("108"));
		RestPreconditions.checkNotNull(userdto.getLastName(), MessageSourceService.getMessage("109"));
		RestPreconditions.checkNotNull(userdto.getMobile(), MessageSourceService.getMessage("110"));
		RestPreconditions.checkArgument(userdto.getEmail().equals("")||userdto.getFirstName().equals("")||
										userdto.getLastName().equals(""), MessageSourceService.getMessage("121"));
		
		Optional<User> optionalUser = userRepository.findByEmail(userdto.getEmail());
		
		RestPreconditions.checkArgument(optionalUser.isPresent(), MessageSourceService.getMessage("115"));

		User user = modelMapper.map(userdto, User.class);
		user.setPassword(passwordEncoder.encode(userdto.getPassword()));
		userRepository.insert(user);
		LOGGER.info(MessageSourceService.getMessage("105"));
		sendAuthenticationMail(user);
	}

	/**
	 * @param registerdto
	 * <p><b>To send mail with activation link</b></p>
	 * @return boolean value
	 * @throws MessagingException
	 */
	public void sendAuthenticationMail(User user) throws ToDoExceptions, MessagingException {
		JwtTokenProvider token = new JwtTokenProvider();
		String validToken = token.generator(user);
		RestPreconditions.checkNotNull(validToken, MessageSourceService.getMessage("114"));
		LOGGER.info("Your token is : " + validToken);
		
		redisRepository.setToken(validToken);
		LOGGER.info("Token set into redis repository");
		
		Email email = new Email();
		email.setTo(user.getEmail());
		email.setSubject("Bellow activation Link");
		email.setBody(ipAddress+"/useractivation/?token=" + validToken);
		//mailProducer.produceMessage(email);
		messageService.sendMessage(email);
	}

	/**
	 * @param registerdto
	 * <p><b>To activate user by setting status true into the database</b></p>
	 * @throws ToDoExceptions 
	 * @throws RegistrationExceptions
	 */
	@Override
	public void activateUser(String userId) throws ToDoExceptions {
		/*JwtTokenProvider claimToken = new JwtTokenProvider();
		String userId = claimToken.parseJWT(token);*/
		Optional<User> optionalUser = userRepository.findById(userId);
		RestPreconditions.checkArgument(!optionalUser.isPresent(), MessageSourceService.getMessage("106"));
		User user = optionalUser.get();
		user.setStatus(true);
		userRepository.save(user);
	}

	/**
	 * @param String emailId
	 * <p> <b>Forgot password operations</b> </p>
	 * @throws ToDoExceptionsng
	 * @throws MessagingException
	 */
	@Override
	public void forgotPassword(String emailId,String token) throws ToDoExceptions, MessagingException {
		RestPreconditions.checkNotNull(emailId, MessageSourceService.getMessage("106"));
		Optional<User> user = userRepository.findByEmail(emailId);
		RestPreconditions.checkNotNull(user, MessageSourceService.getMessage("111"));
		RestPreconditions.checkNotNull(token, MessageSourceService.getMessage("114"));
		
		Email email = new Email();
		email.setTo(user.get().getEmail());
		email.setSubject("Your token is here");
		email.setBody(token);
		mailProducer.produceMessage(email);
	}

	/**@param token
	 * @param passworddto
	 * <p><b>To reset password of the intended user</b></p>
	 **/
	@Override
	public void resetPassword(String token, UserDTO userdto) throws ToDoExceptions {
		if (!userdto.getPassword().equals(userdto.getConfirmPassword())) {
			LOGGER.error(MessageSourceService.getMessage("116"));
			throw new ToDoExceptions(MessageSourceService.getMessage("116"));
		}
		JwtTokenProvider claimToken = new JwtTokenProvider();
		String userId = claimToken.parseJWT(token);
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.get();
		user.setPassword(passwordEncoder.encode(userdto.getPassword()));
		userRepository.save(user);

	}

	
	@Override
	public User getUserByEmailId(String emailId) throws ToDoExceptions {
		RestPreconditions.checkNotNull(emailId, MessageSourceService.getMessage("106"));
		Optional<User>user = userRepository.findByEmail(emailId);
		RestPreconditions.checkNotNull(user, MessageSourceService.getMessage("111"));
		return user.get();
	}

	@Override
	public List<?> getAllUser() throws ToDoExceptions {
		List<User> userList = userRepository.findAll();
		RestPreconditions.checkNotNull(userList, MessageSourceService.getMessage("333"));
		return userList;
		 
	}

	@Override
	public void setProfilePicture(String userId, String imageUrl) throws ToDoExceptions, MalformedURLException {
		RestPreconditions.checkNotNull(imageUrl, MessageSourceService.getMessage("118"));
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.get();
		List<URL>imageList= user.getImageList();
		if(imageList==null) {
			imageList=new ArrayList<URL>();
		}
		URL url=new URL(imageUrl);
		imageList.add(url);
		user.setImageList(imageList);
		userRepository.save(user);
	}
	
	/**
	 * @param file
	 * To Convert multipart file to java file
	 * @return file
	 * @throws IOException
	 */
	public String convertMultipartFileToJavaFile(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    @SuppressWarnings("unused")
		String filename = file.getOriginalFilename();
	    convFile.createNewFile();
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile.getAbsolutePath();
	}
}
