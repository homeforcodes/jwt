package com.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys.entity.ResponseEntity;
import com.sys.entity.User;
import com.sys.service.UserService;
import com.sys.utils.JWTUtil;
import com.sys.utils.RedisTemplateUtil;

@RestController
@RequestMapping(value = "sys")
public class SysController {

	@Autowired
	private UserService userService;
	@Autowired
	private RedisTemplateUtil redisTemplateUtil;
	
	@PostMapping(value = "login")
	public Object login(String username, String password) {
		//添加缓存逻辑
		User cacheUser = redisTemplateUtil.getUser("user:"+username);
		if(cacheUser == null) {
			User user = userService.findByUsernameAndPassword(username, password);
			if(user != null) {
				String token = JWTUtil.getToken(user.getUsername(), user.getPassword());
				//添加到缓存
				redisTemplateUtil.putToken(token, "user:"+user.getUsername()+":token", 5);
				redisTemplateUtil.putUserInfo(user, 5);
				return new ResponseEntity().setCode(2000).setMsg("登录成功").setData(token);
			}
		}else {
			String token = JWTUtil.getToken(cacheUser.getUsername(), cacheUser.getPassword());
			redisTemplateUtil.putToken(token, "user:"+cacheUser.getUsername()+":token", 5);
			return new ResponseEntity().setCode(2000).setMsg("登录成功").setData(token);
		}
		return new ResponseEntity().setCode(1000).setMsg("登录失败");
	}
	
	@GetMapping(value = "logout")
	public Object logout(String username) {
		boolean deleteUser = redisTemplateUtil.delete("user:"+username);
		redisTemplateUtil.delete("user:"+username+"token");
		if(deleteUser) {
			return new ResponseEntity().setCode(2001).setMsg("登出成功");
		}else {
			return new ResponseEntity().setCode(1002).setMsg("登出失败");
		}
	}
}
