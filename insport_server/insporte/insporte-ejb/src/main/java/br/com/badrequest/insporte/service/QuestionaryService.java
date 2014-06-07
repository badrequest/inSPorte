package br.com.badrequest.insporte.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;

import br.com.badrequest.insporte.model.Questionary;
import br.com.badrequest.insporte.model.QuestionaryAnswer;

@Stateless
public class QuestionaryService {

	@Inject
	EntityManager em;
	
	@Inject
	UserService userService;

	@Inject
	BusService busService;
	
	@SuppressWarnings("unchecked")
	public List<Questionary> getAllQuestionaries() {
		
		try {
			EasyCriteria<Questionary> criteria = EasyCriteriaFactory.createQueryCriteria(em, Questionary.class);
			criteria.leftJoinFetch("questions");
			criteria.leftJoinFetch("questions.options");
			criteria.setDistinctTrue();
			
			List<Questionary> l = criteria.getResultList();
		
			return l;
		} catch (NoResultException noResultException) {
			return null;
		}
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
