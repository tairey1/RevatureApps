package com.revature.service;

import org.apache.log4j.Logger;

import com.revature.dao.UserDao;
import com.revature.pojos.User;

public class UserService {
	
	static Logger log = Logger.getLogger(UserService.class);
	static UserDao uDao = new UserDao();
	
	public static User validateUser(String username, String password) {
		User u = uDao.findByUsername(username);
		
		log.debug(u);
		if(u.getId()==0 || u.getPassword().equals(password)) {
			return u;
		}
		else {
			return new User();
		}
	}
	
	public static User findById(int id) {
		User u = uDao.findByID(id);
		return u;
	}
	
	public static User findByUsername(String username) {
		User u = uDao.findByUsername(username);
		return u;
	}
	
	public void insertUser(User u) {
		uDao.insert(u);
	}

}
