package com.bridgelabz.userservice.utilservice.securityservice;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bridgelabz.userservice.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author yuga
 * @since 09/07/2018
 *<p><b>To generate token and provide to the user</b></p>
 */
@Service
public class JwtTokenProvider {
	final static String KEY = "yuga";
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	/**
	 * @param registerdto
	 * @return token
	 * <p><b>To generate the token<p><b>
	 */
	public String generator(User user) {
		String userId = user.getId();
		return Jwts.builder().setSubject(userId).signWith(SignatureAlgorithm.HS256, KEY).compact();
	}

	/**
	 * 
	 * @param jwt
	 * @return email
	 * <p><b>To get claims of the token </b></p>
	 */
	public String parseJWT(String jwtToken) {
		logger.info(jwtToken);
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY)).parseClaimsJws(jwtToken)
				.getBody();
		logger.info("Subject: " + claims.getSubject());
		return claims.getSubject();
	}
}