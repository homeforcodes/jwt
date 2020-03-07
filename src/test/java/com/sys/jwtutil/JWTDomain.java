package com.sys.jwtutil;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTDomain {

	public static void main(String[] args) throws InterruptedException {
		long exp = System.currentTimeMillis()+ 5*1000; //5s
		JwtBuilder builder = Jwts.builder().setId("888").setSubject("小白").setIssuedAt(new Date())// 设置签发时间
				.signWith(SignatureAlgorithm.HS256, "xiaocai").setExpiration(new Date(exp));// 设置签名秘钥
		String token = builder.compact();
		System.out.println(token);
		//Thread.sleep(5000);
		Claims claims = Jwts.parser().setSigningKey("xiaocai").parseClaimsJws(token).getBody();
		System.out.println("id:" + claims.getId());
		System.out.println("subject:" + claims.getSubject());
		System.out.println("IssuedAt:" + claims.getIssuedAt());
	}
}
