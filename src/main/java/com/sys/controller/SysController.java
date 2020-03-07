package com.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys.entity.ResponseEntity;
import com.sys.entity.User;
import com.sys.service.UserService;
import com.sys.utils.JWTUtil;

@RestController
@RequestMapping(value = "sys")
public class SysController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "login")
	public Object login(String username, String password) {
		User user = userService.findByUsernameAndPassword(username, password);
		if(user != null) {
			String token = JWTUtil.getToken(user.getUsername(), user.getPassword());
			return new ResponseEntity().setCode(2000).setMsg("登录成功").setData(token);
		}
		return new ResponseEntity().setCode(1000).setMsg("登录失败");
	}
}
