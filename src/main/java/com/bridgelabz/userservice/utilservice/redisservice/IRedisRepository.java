package com.bridgelabz.userservice.utilservice.redisservice;

/**
 * @author yuga
 *
 */
public interface IRedisRepository {

	/**
	 * @param token
	 */
	void setToken(String token);
	/**
	 * @param userId
	 * @return
	 */
	public String getToken(String userId);
	/**
	 * @param userId
	 */
	public void deleteToken(String userId) ;

}