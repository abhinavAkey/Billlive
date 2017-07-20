package com.beatus.billlive.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beatus.billlive.domain.model.UserData;


@Repository
public class UserRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	public String addUser(UserData p) {
		try {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("User saved successfully, User Details="+p);
		return "Y";
		} catch (Exception e) {
		return "N";	
		}
		
	}

	public void updateUser(UserData p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("User updated successfully, User Details="+p);
	}

	@SuppressWarnings("unchecked")
	public List<UserData> getAllUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserData> personsList = session.createQuery("from User").list();
		for(UserData p : personsList){
			logger.info("User List::"+p);
		}
		return personsList;
	}

	public UserData getUserById(String uId) {
		Session session = this.sessionFactory.getCurrentSession();		
		UserData userdata = (UserData) session.load(UserData.class, new String(uId));
		logger.info("User loaded successfully, User details="+userdata);
		return userdata;
	}

	
	public void removeUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		UserData p = (UserData) session.load(UserData.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("User deleted successfully, person details="+p);
	}

}