package com.sys.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {


	/**
	 * 根据用户名和密码创建JWT
	 * @param username 用户名
	 * @param password 密码
	 * @return 生成token
	 */
	public static String getToken(String username, String password) {
		Date exp = new Date(System.currentTimeMillis() + 5 * 60 * 1000);
		Algorithm algorithm = Algorithm.HMAC256(password);
		String token = JWT.create().withClaim("username", username).withExpiresAt(exp).sign(algorithm);
		return token;
	}
	
	/**
	 * 根据token 获取用户名
	 * @param token 加密的字符串
	 * @return 用户名
	 */
	public static String getUserName(String token) {
		DecodedJWT decode = JWT.decode(token);
		Claim claim = decode.getClaim("username");
		return claim.asString();
	}
	
	/**
	 * 校验是不是username和password信息生成的JWT token
	 * @param username 用户名
	 * @param password 密码
	 * @param token JWT
	 * @return 
	 */
	public static boolean verify(String username, String password, String token) {
		Algorithm algorithm = Algorithm.HMAC256(password);
		JWTVerifier build = JWT.require(algorithm).withClaim("username", username).build();
		try {
			build.verify(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
