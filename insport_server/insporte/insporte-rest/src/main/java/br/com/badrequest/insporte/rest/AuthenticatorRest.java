package br.com.badrequest.insporte.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.badrequest.insporte.exception.InvalidUID;
import br.com.badrequest.insporte.model.User;
import br.com.badrequest.insporte.rest.model.Response;
import br.com.badrequest.insporte.rest.model.auth.Auth;
import br.com.badrequest.insporte.rest.util.GoogleChecker;
import br.com.badrequest.insporte.service.UserService;
import br.com.badrequest.insporte.util.PasswordGenerator;
import br.com.badrequest.insporte.util.PasswordGenerator.Mode;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;

/**
 * JAX-RS Authenticator
 * 
 * This class produces a RESTful service to authenticate Users
 * table.
 */
@Path("/auth")
@RequestScoped
@SuppressWarnings("unchecked")
public class AuthenticatorRest {

	@Inject
	private UserService userService;

	@POST
	@Path("/simple")
	@Produces(MediaType.APPLICATION_JSON)
	public String simple(String json) {
		Auth auth = new Gson().fromJson(json, Auth.class);

		try {
			User user = null;
            
            user = userService.getUserByUUID(auth.uuid);
	            
            if (user == null) {
            	user = new User();
            	user.setUuid(auth.uuid);
            	user.setPassword(PasswordGenerator.generateRandomString(20, Mode.ALPHANUMERIC));
            	userService.insert(user);
            } else {
            	throw new InvalidUID();
            }
            
			return new Gson().toJson(new Response(user.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
			return new Gson().toJson(new Response(Response.FAIL));
		}
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addUser(String json) {
		Auth auth = new Gson().fromJson(json, Auth.class);

		try {
			User user = null;
			String email = null;
            
			GoogleChecker checker = new GoogleChecker(new String[]{"?"}, "?");
            
			GoogleIdToken.Payload payload = checker.check(auth.token);
            
            System.out.println("Token Auth: " + auth.token);
            
            if (payload.getEmailVerified()) {
                email = payload.getEmail();
            
	            user = userService.getUserByUUID(auth.uuid);
	            
	            if (user == null) {
	            	user = new User();
	            	user.setEmail(email);
	            	user.setUuid(auth.uuid);
	            	user.setPassword(PasswordGenerator.generateRandomString(20, Mode.ALPHANUMERIC));
	            	userService.insert(user);
	            } else {
	            	throw new InvalidUID();
	            }
	            
				return new Gson().toJson(new Response(user.getPassword()));
            } else {
    			return new Gson().toJson(new Response(Response.FAIL));
            }
		} catch (Exception e) {
			e.printStackTrace();
			return new Gson().toJson(new Response(Response.FAIL));
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String auth(String json) {
		return new Gson().toJson(new Response(Response.OK));
	}
	
}
