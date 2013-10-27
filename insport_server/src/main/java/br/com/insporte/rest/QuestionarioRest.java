package br.com.insporte.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import br.com.insporte.rest.model.Response;
import br.com.insporte.rest.model.questionario.Questionario;
import br.com.insporte.rest.model.questionario.QuestionarioResponse;
import br.com.insporte.service.RespostaService;

/**
 * REST para Envio de Respostas de Questionarios
 */
@Path("/add")
public class QuestionarioRest {

	@Inject
	RespostaService respostaService;
	
	@BadgerFish
	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public QuestionarioResponse adicionarQuestionario(Questionario questionario) {

		respostaService.salvarResposta(questionario);
		
		return new QuestionarioResponse(Response.OK);
	}
}