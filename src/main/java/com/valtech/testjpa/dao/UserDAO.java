package com.valtech.testjpa.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.valtech.testjpa.domain.User;

@Repository
@Transactional
public class UserDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void storeUser(User user) {
		User merged = this.em.merge(user);
		this.em.flush();
		user.setId(merged.getId());
	}
	
	public Collection<User> findAllUsers() {
		return this.em.createQuery("select user from User user").getResultList();
	}
	
	public User findUserById(Integer id) {
		return this.em.find(User.class, id);
	}
}
