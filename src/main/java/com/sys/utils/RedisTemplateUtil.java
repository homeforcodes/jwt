package com.sys.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sys.entity.User;

@Component
public class RedisTemplateUtil {

	@Autowired
	private StringRedisTemplate template;

	/**
	 * 添加用户缓存
	 * 
	 * @param user 用户实体
	 * @param time 有效时间
	 */
	public  void putUserInfo(User user, int time) {
		String userInfo = JSON.toJSONString(user);
		template.opsForValue().set("user:" + user.getUsername(), userInfo, time, TimeUnit.MINUTES);
	}

	/**
	 * 获取用户
	 * 
	 * @param key
	 * @return
	 */
	public User getUser(String key) {
		String userInfo = template.opsForValue().get(key);
		User user = JSONObject.parseObject(userInfo, User.class);
		template.expire(key, 5, TimeUnit.MINUTES);// 更新缓存存活时间
		return user;
	}

	/**
	 * 删除用户
	 * 
	 * @param key
	 * @return
	 */
	public boolean delete(String key) {
		Boolean delete = template.delete(key);
		return delete;
	}

	/**
	 * 获取缓存中的token
	 * 
	 * @param key
	 * @return
	 */
	public String getToken(String key) {
		return template.opsForValue().get(key);
	}

	public void putToken(String token, String key, int time) {
		template.opsForValue().set(key, token, time, TimeUnit.MINUTES);
	}

	

}
