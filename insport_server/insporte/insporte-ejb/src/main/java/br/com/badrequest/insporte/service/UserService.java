package br.com.badrequest.insporte.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.badrequest.insporte.model.User;

@Stateless
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

	public User getUserByUUID(String uuid) {
		try {
			Query userQuery = em
					.createQuery("SELECT u FROM User u WHERE u.uuid = :uuid");

			userQuery.setParameter("uuid", uuid);
			User user = (User) userQuery.getSingleResult();
			
			return user;
		} catch (NoResultException noResultException) {
			return null;
		}
	}
	
	public void insert(User user) {
		em.persist(user);
	}
	
	
	public boolean validateUser(String uuid, String pw) {
		
		if (uuid == null || uuid.isEmpty()) {
			return false;
		}
		
		User u = this.getUserByUUID(uuid);
		
		if (u.getPassword().equals(pw)) {
			return true;
		}
		
		return false;
	}

}
