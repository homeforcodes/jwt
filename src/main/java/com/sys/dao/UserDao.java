package com.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sys.entity.User;

public interface UserDao extends JpaRepository<User, Long> {

	User findByUsernameAndPassword(String username, String password);

}
