package br.com.badrequest.insporte.rest;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.badrequest.insporte.model.Questionary;
import br.com.badrequest.insporte.rest.model.Response;
import br.com.badrequest.insporte.rest.util.GsonFactory;
import br.com.badrequest.insporte.service.QuestionaryService;

import com.google.gson.Gson;

/**
 * JAX-RS 
 * 
 * This class produces a RESTful service to read the contents of the Questionaries
 * table.
 */
@Path("/questionary")
@RequestScoped
@SuppressWarnings("unchecked")
public class QuestionaryRest {

	@Inject
	private QuestionaryService questionaryService;
	
	private static final List<String> attrs = Arrays.asList(
			"Questionary.id", "Questionary.name", "Questionary.questions",
			"Question.id", "Question.description", "Question.options",
			"Option.id", "Option.description");

	@GET
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	public String listAllQuestionaries() {
		List<Questionary> results = questionaryService.getAllQuestionaries();
		return new GsonFactory().toJson(attrs, results);
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	public String getQuestionary(@PathParam("id") long id) {
		Questionary questionary = questionaryService.getQuestionary(id);
		
		if (questionary == null) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
		
		return new GsonFactory().toJson(attrs, questionary);
	}
}
