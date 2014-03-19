package br.com.badrequest.insporte.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.badrequest.insporte.rest.model.Response;
import br.com.badrequest.insporte.rest.model.image.GetImage;
import br.com.badrequest.insporte.rest.model.image.ImageResponse;
import br.com.badrequest.insporte.rest.model.image.ImagemRequest;
import br.com.badrequest.insporte.service.ImageService;

import com.google.gson.Gson;

/**
 * JAX-RS
 * 
 * This class produces a RESTful service to manipulate images table.
 */
@Path("/image")
@RequestScoped
@SuppressWarnings("unchecked")
public class ImageRest {

	@Inject
	private ImageService imageService;

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertImage(String json) {
		try {
			ImagemRequest imagemRequest = new Gson().fromJson(json,
					ImagemRequest.class);

			imageService.updateImage(imagemRequest.imagem.idImagem,
					imagemRequest.imagem.img);

			return new Gson().toJson(new Response(Response.OK));
		} catch (Exception e) {
			return new Gson().toJson(new Response(Response.FAIL));
		}

	}
	
	@POST
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getImage(String json) {
		try {
			GetImage getImage = new Gson().fromJson(json,
					GetImage.class);

			String b64 = imageService.getImageBase64(getImage.idImagem);

			if (b64 != null) {
				return new Gson().toJson(new ImageResponse(Response.OK, b64));
			}
		} catch (Exception e) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
		
		return new Gson().toJson(new Response(Response.FAIL));
	}

}
