package com.sys.config;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.sys.entity.ResponseEntity;
import com.sys.entity.User;
import com.sys.service.UserService;
import com.sys.utils.JWTUtil;
import com.sys.utils.RedisTemplateUtil;

@Component
public class TokenService implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	@Autowired
	private RedisTemplateUtil redisTemplateUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("token");
		String userName = JWTUtil.getUserName(token);
		// 获取缓存中的token
		String cacheToken = redisTemplateUtil.getToken("user:" + userName + ":token");
		if(token !=null && cacheToken !=null && token.equals(cacheToken)) {
			if (cacheToken != null) {
				//获取缓存中的用户信息
				User userInfo = redisTemplateUtil.getUser("user:" + userName);
				User user = null;
				if (userInfo == null) {
					user = userService.findByUsername(userName);
				} else {
					user = userInfo;
				}
				if (token != null && token != "") {
					boolean verify = JWTUtil.verify(user.getUsername(), user.getPassword(), token);
					// token验证成功更新token有效时间 保持在有效时间为动态的5分钟
					if (verify) {
						token = JWTUtil.getToken(user.getUsername(), user.getPassword());
						// 将新的token替换缓存中的老token
						redisTemplateUtil.putToken(token, "user:" + userName + ":token", 5);
						response.setHeader("token", token);
						return verify;
					}
				}
			}
		}
		// 如果验证失败 返回状态码
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		Object json = JSON.toJSON(new ResponseEntity().setCode(1001).setMsg("token认证失败"));
		writer.print(json);
		return false;
	}
}
