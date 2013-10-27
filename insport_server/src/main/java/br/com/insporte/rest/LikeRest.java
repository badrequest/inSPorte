package br.com.insporte.rest;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.springframework.stereotype.Controller;

import br.com.insporte.rest.model.like.LikeGet;
import br.com.insporte.rest.model.like.LikeInsert;
import br.com.insporte.rest.model.like.LikeResponse;
import br.com.insporte.rest.model.like.graph.LikeGraph;
import br.com.insporte.rest.model.like.graph.LikeQtd;
import br.com.insporte.service.LinhaService;

/**
 * Rest para Controle de Likes e Unlikes
 */
@Path("/")
@Controller
public class LikeRest {
	
	@Inject
	LinhaService linhaService;
	
	@BadgerFish
	@POST
	@Path("/set-like")
	@Consumes("application/json")
	@Produces("application/json")
	public br.com.insporte.rest.model.Response like(LikeInsert like) {
		linhaService.salvarLike(like.getLike().getId(), like.getAuth(), like.getLike().getStatus());
		return new br.com.insporte.rest.model.Response(br.com.insporte.rest.model.Response.OK);
	}
	
	@BadgerFish
	@POST
	@Path("/get-like")
	@Produces("application/json")
	@Consumes("application/json")
	public LikeResponse getLike(LikeGet likeGet) {
		Boolean b = linhaService.obterLike(likeGet.getId(), likeGet.getAuth());
		
		if (b == null) {
			return new LikeResponse(br.com.insporte.rest.model.Response.FAIL, null);
		}
		
		return new LikeResponse(br.com.insporte.rest.model.Response.OK, b);
	}
	
	@BadgerFish
	@GET
	@Path("/likes")
	@Produces("application/json")
	public LikeGraph getLikeGraph() {
		
		LikeGraph likeGraph = new LikeGraph();
		
		likeGraph.setLikes(Arrays.asList(
				new LikeQtd("Linha 1", 20L),
				new LikeQtd("Linha 2", 50L),
				new LikeQtd("Linha 3", 30L)));
		
		return likeGraph;
	}
	
	@BadgerFish
	@GET
	@Path("/unlikes")
	@Produces("application/json")
	public LikeGraph getUnlikeGraph() {
		
		LikeGraph likeGraph = new LikeGraph();
		
		likeGraph.setLikes(Arrays.asList(
				new LikeQtd("Linha 1", 20L),
				new LikeQtd("Linha 2", 50L),
				new LikeQtd("Linha 3", 30L)));

		return likeGraph;
	}
}