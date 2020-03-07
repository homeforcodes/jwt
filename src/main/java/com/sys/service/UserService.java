package com.sys.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.UserDao;
import com.sys.entity.User;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public User findByUsernameAndPassword(String username, String password) {
		return userDao.findByUsernameAndPassword(username, password);
	}

	public User findByUsername(String userName) {
		return userDao.findByUsername(userName);
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	
}
