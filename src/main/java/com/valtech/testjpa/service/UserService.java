package com.valtech.testjpa.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valtech.testjpa.dao.UserDAO;
import com.valtech.testjpa.domain.User;

@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserDAO dao;

	public UserService () {
	}
	
	public Collection<User> getAllUsers() {
		logger.info("Appel de findAllUsers");
		return dao.findAllUsers();
	}
	
	public User updateUser(User user) {
		logger.info("Appel de updateUser");
		
		// Exemple d'utilisation de placeholders avec slf4j
		logger.debug("Modification du client {}, {}", user.getLastName(), user.getFirstName());
		dao.storeUser(user);
		User updatedUser = dao.findUserById(user.getId());
		return updatedUser;
	}
}
