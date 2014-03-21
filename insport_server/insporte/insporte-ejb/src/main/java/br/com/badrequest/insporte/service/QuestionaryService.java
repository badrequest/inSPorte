package br.com.badrequest.insporte.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.badrequest.insporte.model.Questionary;

@Stateful
public class QuestionaryService {

	@Inject
	EntityManager em;
	
	@Inject
	UserService userService;

	@Inject
	BusService busService;
	
	@SuppressWarnings("unchecked")
	public List<Questionary> getAllQuestionaries() {
		
		String jpql = "SELECT a FROM Questionary a LEFT JOIN FETCH a.questions b LEFT JOIN FETCH b.options";

		List<Questionary> questionaries = 
				(List<Questionary>) em.createQuery(jpql).getResultList();
		
		return questionaries;
	}
	
	public Questionary getQuestionary(Long id) {
		try {
			String jpql = "SELECT a FROM Questionary a LEFT JOIN FETCH a.questions b LEFT JOIN FETCH b.options WHERE a.id = :id";
			Questionary questionary = (Questionary) em.createQuery(jpql).setParameter("id", id).getSingleResult();
			return questionary;
		} catch(NoResultException noResultException) {
			return null;
		}
	}
	
}
