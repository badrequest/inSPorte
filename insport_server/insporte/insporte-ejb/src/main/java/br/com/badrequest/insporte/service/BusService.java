package br.com.badrequest.insporte.service;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.badrequest.insporte.model.BusLine;

@Stateful
public class BusService {

	@Inject
	EntityManager em;

	public BusLine getBusLine(String code) {
		
		try {
			BusLine busLine = (BusLine) em
					.createQuery("SELECT bl from BusLine bl where bl.code = :code")
					.setParameter("code", code).getSingleResult();
			
			return busLine;
		} catch (NoResultException noResultException) {
			return null;
		}
		
	}
}
