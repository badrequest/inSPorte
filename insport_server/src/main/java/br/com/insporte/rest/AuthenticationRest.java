package br.com.insporte.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.springframework.stereotype.Controller;

import br.com.insporte.rest.model.Response;
import br.com.insporte.rest.model.auth.Authentication;
import br.com.insporte.service.UsuarioService;

/** 
 * REST para Autenticacao
 */
@Path("/auth")
@Controller
public class AuthenticationRest {
	
	@Inject
	UsuarioService usuarioService;
	
	@BadgerFish
	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public Response autenticar(Authentication authentication) {
		
		/*
		 * Insere ou valida o usuario
		 */
		usuarioService.inserirUsuario(authentication.getAuth());
				
		return new Response(Response.OK);
	}
}