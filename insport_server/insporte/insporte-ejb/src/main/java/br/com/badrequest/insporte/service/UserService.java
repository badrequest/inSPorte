package br.com.badrequest.insporte.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.badrequest.insporte.model.User;

@Stateful
public class UserService {

	@Inject
	EntityManager em;

	public User getUser(Long id) {
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		Query userQuery = em
				.createQuery("SELECT u FROM User u ORDER by u.document");

		List<User> users = (List<User>) userQuery.getResultList();

		return users;
	}

	public User getUser(String bu, String document) {
		try {
			Query userQuery = em
					.createQuery("SELECT u FROM User u WHERE u.bu = :bu AND u.document = :doc");

			userQuery.setParameter("bu", bu);
			userQuery.setParameter("doc", document);
			User user = (User) userQuery.getSingleResult();
			
			return user;
		} catch (NoResultException noResultException) {
			return null;
		}
	}
	
	public User getUser(String email) {
		try {
			Query userQuery = em
					.createQuery("SELECT u FROM User u WHERE u.email = :email");

			userQuery.setParameter("email", email);
			User user = (User) userQuery.getSingleResult();
			
			return user;
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	public void insert(User user) {
		em.persist(user);
	}
	
	
	public boolean validateUser(String email, String pw) {
		
		if (email == null || email.isEmpty()) {
			return false;
		}
		
		User u = this.getUser(email);
		
		if (u.getPassword().equals(pw)) {
			return true;
		}
		
		return false;
	}

}
