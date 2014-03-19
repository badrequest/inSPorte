package br.com.badrequest.insporte.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addUser(String json) {
		Auth auth = new Gson().fromJson(json, Auth.class);

		try {
			User user = null;
			String email = null;
            
			GoogleChecker checker = new GoogleChecker(new String[]{"549088494676-m2ctid6cpll6ttd4j3p6rk4d8ug49eri.apps.googleusercontent.com"}, "549088494676-ukvqmup4o945c2memdcgudc631nm1mdk.apps.googleusercontent.com");
            GoogleIdToken.Payload payload = checker.check(auth.token);
            
            if (payload.getEmailVerified()) {
                email = payload.getEmail();
            
	            user = userService.getUser(email);
	            
	            if (user == null) {
	            	user = new User();
	            	user.setEmail(email);
	            	user.setPassword(PasswordGenerator.generateRandomString(20, Mode.ALPHANUMERIC));
	            	userService.insert(user);
	            }
	            
				return new Gson().toJson(new Response(user.getPassword()));
            } else {
    			return new Gson().toJson(new Response(Response.FAIL));
            }
		} catch (Exception e) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String auth(String json) {
		return new Gson().toJson(new Response(Response.OK));
	}
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getAllUsers() {
//		List<User> results = userService.getAllUsers();
//		
//		if (results == null) {
//			results = new ArrayList<User>();
//		}
//		
//		return new Gson().toJson(results);
//	}
//
//	@GET
//	@Path("/{id:[0-9][0-9]*}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getUser(@PathParam("id") long id) {
//		User user = userService.getUser(id);
//		
//		if (user == null) {
//			return new Gson().toJson(new Response(Response.FAIL));
//		}
//		
//		return new Gson().toJson(user);
//	}
}
