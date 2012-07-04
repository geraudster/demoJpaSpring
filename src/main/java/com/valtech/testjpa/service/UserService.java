package com.valtech.testjpa.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valtech.testjpa.dao.UserDAO;
import com.valtech.testjpa.domain.User;

@Service
public class UserService {

	@Autowired
	UserDAO dao;

	public UserService () {
	}
	
	public Collection<User> getAllUsers() {
		return dao.findAllUsers();
	}
	
	public User updateUser(User user) {
		dao.storeUser(user);
		User updatedUser = dao.findUserById(user.getId());
		return updatedUser;
	}
}
