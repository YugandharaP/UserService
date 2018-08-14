package com.bridgelabz.userservice.utilservice.redisservice;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bridgelabz.userservice.model.User;
import com.bridgelabz.userservice.utilservice.securityservice.JwtTokenProvider;

/**
 * @author yuga
 * @since 19/07/2018
 *        <p>
 *        <b>To store token generated at the time of login operation and
 *        provides setToken,getToken and deleteToken methoods.Redis is an Open
 *        source in-memory data structure store, used as a database, cache and
 *        message broker</b>
 *        </p>
 *
 */
@Repository
public class RedisRepositoryImplementation implements IRedisRepository{

	@Autowired
	JwtTokenProvider tokenProvider;
	
	private RedisTemplate<String, User> redisTemplate;
	private static HashOperations<String, String, String> hashOperations;
	private static String KEY = "TOKEN";

	@Autowired
	public RedisRepositoryImplementation(RedisTemplate<String, User> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private static Logger logger = LoggerFactory.getLogger(RedisRepositoryImplementation.class);

	/**
	 * To initialize hash operations and this method MUST be invoked before the class is put into service.
	 */
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	/**
	 * @param clientId
	 * @param jwtToken
	 */
	@Override
	public void setToken(String jwtToken) {
		String userId = tokenProvider.parseJWT(jwtToken);
		hashOperations.put(KEY, userId, jwtToken);
		logger.info("Token set in redis");
	}

	/**
	 * @param clientId
	 * @return token
	 */
	@Override
	public String getToken(String userId) {
		logger.info("Getting token from redis");
		return hashOperations.get(KEY, userId);
	}

	/**
	 * @param clientId
	 */
	@Override
	public void deleteToken(String userId) {
		logger.info("Deleting token from redis");
		hashOperations.delete(KEY, userId);
	}
}
