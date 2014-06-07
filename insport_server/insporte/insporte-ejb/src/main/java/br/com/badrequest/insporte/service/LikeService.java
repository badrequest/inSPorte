package br.com.badrequest.insporte.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.badrequest.insporte.model.BusLine;
import br.com.badrequest.insporte.model.Like;
import br.com.badrequest.insporte.model.User;

@Stateless
public class LikeService {

	@Inject
	EntityManager em;

	@Inject
	UserService userService;

	@Inject
	BusService busService;

	public boolean addLike(String uuid, String busLineCode, boolean likeStatus) {

		BusLine busLine = busService.getBusLine(busLineCode);
		if (busLine == null) {
			return false;
		}

		User user = userService.getUserByUUID(uuid);
		if (user == null) {
			return false;
		}

		Like like = this.getLike(uuid, busLineCode);

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

	public Like getLike(String uuid, String busLineCode) {

		String jpql = "SELECT l FROM Like l LEFT JOIN FETCH l.user u LEFT JOIN FETCH l.busLine bus "
				+ "WHERE u.uuid = :uuid AND bus.code = :code";

		try {
			Like like = (Like) em.createQuery(jpql)
					.setParameter("uuid", uuid)
					.setParameter("code", busLineCode).getSingleResult();
			return like;
		} catch (NoResultException noResultException) {
			return null;
		}

	}

}
