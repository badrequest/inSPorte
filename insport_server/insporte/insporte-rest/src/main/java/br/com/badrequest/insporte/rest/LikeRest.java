package br.com.badrequest.insporte.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.badrequest.insporte.model.Like;
import br.com.badrequest.insporte.rest.model.Response;
import br.com.badrequest.insporte.rest.model.like.AddLike;
import br.com.badrequest.insporte.rest.model.like.GetLike;
import br.com.badrequest.insporte.rest.model.like.LikeResponse;
import br.com.badrequest.insporte.service.LikeService;
import br.com.badrequest.insporte.service.UserService;

import com.google.gson.Gson;

/**
 * JAX-RS Like Rest
 * 
 * This class produces a RESTful service to read and insert likes.
 */
@Path("/likes")
@RequestScoped
@SuppressWarnings("unchecked")
public class LikeRest {

	@Inject
	private LikeService likeService;
	
	@Inject
	private UserService userService;

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addLike(String json) {
		
		AddLike addlike = new Gson().fromJson(json, AddLike.class);
		
		if (!userService.validateUser(addlike.auth.uuid, addlike.auth.senha)) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
		
		boolean ret = likeService.addLike(addlike.auth.uuid, 
				addlike.like.id, 
				addlike.like.status);
		
		if (ret) {
			return new Gson().toJson(new Response(Response.OK));
		} else {
			return new Gson().toJson(new Response(Response.FAIL));
		}
	}
	
	@POST
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getLike(String json) {
	
		GetLike getlike = new Gson().fromJson(json, GetLike.class);
		
		Like like = likeService.getLike(getlike.auth.uuid, getlike.id);
		
		if (like == null) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
		
		return new Gson().toJson(new LikeResponse(Response.OK, like.getStatus()));
	}
	
}
