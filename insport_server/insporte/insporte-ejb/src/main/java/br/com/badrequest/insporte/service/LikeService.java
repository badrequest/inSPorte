package br.com.badrequest.insporte.service;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.badrequest.insporte.model.BusLine;
import br.com.badrequest.insporte.model.Like;
import br.com.badrequest.insporte.model.User;

@Stateful
public class LikeService {

	@Inject
	EntityManager em;
	
	@Inject
	UserService userService;

	@Inject
	BusService busService;
	
	public boolean addLike(String email, String busLineId, boolean likeStatus) {
		
		BusLine busLine = busService.getBusLine(busLineId);
		if (busLine == null) {
			return false;
		}
		
		User user = userService.getUser(email);
		if (user == null) {
			return false;
		}
		
		Like like = this.getLike(email, busLineId);
		
		if (like == null) {
			like = new Like();
			like.setBusLine(busLine);
			like.setStatus(likeStatus);
			like.setUser(user);
			em.persist(like);
		} else {
			like.setStatus(likeStatus);
			em.merge(like); 
		}
		
		return true;
	}
	
	public boolean addLike(String bu, String document, String busLineId, boolean likeStatus) {
		
		BusLine busLine = busService.getBusLine(busLineId);
		if (busLine == null) {
			return false;
		}

		User user = userService.getUser(bu, document);
		if (user == null) {
			return false;
		}
		
		Like like = this.getLike(bu, document, busLineId);
		
		if (like == null) {
			like = new Like();
			like.setBusLine(busLine);
			like.setStatus(likeStatus);
			like.setUser(user);
			em.persist(like);
		} else {
			like.setStatus(likeStatus);
			em.merge(like); 
		}
		
		return true;
	}

	public Like getLike(String email, String busLineId) {
		
		String jpql = "SELECT l FROM Like l LEFT JOIN FETCH l.user u LEFT JOIN FETCH l.busLine bus " +
				"WHERE u.email = :email AND bus.id = :bid";
		
		try {
			Like like = (Like) em.createQuery(jpql)
				.setParameter("email", email)
				.setParameter("bid", busLineId)
				.getSingleResult();
			return like;
		} catch(NoResultException noResultException) {
			return null;
		}
		
	}
	
	public Like getLike(String bu, String document, String busLineId) {
		
		String jpql = "SELECT l FROM Like l LEFT JOIN FETCH l.user u LEFT JOIN FETCH l.busLine bus " +
				"WHERE u.bu = :bu AND u.document = :document AND bus.id = :bid";
		
		try {
			Like like = (Like) em.createQuery(jpql)
				.setParameter("bu", bu)
				.setParameter("document", document)
				.setParameter("bid", busLineId)
				.getSingleResult();
			return like;
		} catch(NoResultException noResultException) {
			return null;
		}
		
	}
	
}
