package com.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys.entity.User;
import com.sys.service.UserService;

@RestController
@RequestMapping(value = "sys")
public class SysController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "login")
	public Object login(String username, String password) {
		User user = userService.findByUsernameAndPassword(username, password);
		if(user != null) {
			
		}
		return null;
	}
}
