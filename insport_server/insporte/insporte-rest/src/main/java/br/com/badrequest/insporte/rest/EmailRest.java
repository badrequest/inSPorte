package br.com.badrequest.insporte.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.badrequest.insporte.rest.model.Response;
import br.com.badrequest.insporte.rest.model.email.EmailForm;
import br.com.badrequest.insporte.service.EmailService;

import com.google.gson.Gson;

/**
 * JAX-RS Email Service Rest
 * 
 * This class produces a RESTful service to send emails
 */
@Path("/email")
@RequestScoped
@SuppressWarnings("unchecked")
public class EmailRest {

	@Inject
	private EmailService emailService;

	@POST
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String send(String json) {
		
		EmailForm form = new Gson().fromJson(json, EmailForm.class);
		emailService.send(form.nome, form.email, form.assunto, form.mensagem);
		
		return new Gson().toJson(new Response(Response.OK));
	}
	
}
