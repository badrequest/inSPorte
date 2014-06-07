package br.com.badrequest.insporte.rest;

import java.io.IOException;
import java.io.OutputStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import br.com.badrequest.insporte.exception.InvalidUser;
import br.com.badrequest.insporte.rest.model.Response;
import br.com.badrequest.insporte.rest.model.image.GetImage;
import br.com.badrequest.insporte.rest.model.image.ImageResponse;
import br.com.badrequest.insporte.rest.model.image.ImagemRequest;
import br.com.badrequest.insporte.service.ImageService;
import br.com.badrequest.insporte.service.UserService;

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

	@Inject
	private UserService userService;
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertImage(String json) {
		try {
			ImagemRequest imagemRequest = new Gson().fromJson(json,
					ImagemRequest.class);

			if (!userService.validateUser(imagemRequest.auth.uuid, imagemRequest.auth.senha)) {
				throw new InvalidUser();
			}
			
			imageService.updateImage(imagemRequest.imagem.idImagem,
					imagemRequest.imagem.img);

			return new Gson().toJson(new Response(Response.OK));
		} catch (Exception e) {
			e.printStackTrace();
			return new Gson().toJson(new Response(Response.FAIL));
		}

	}
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({"image/png"})
	public javax.ws.rs.core.Response getImageById(@PathParam("id") String id) {
		try {

			final byte[] img = imageService.getImageBytes(Long.valueOf(id));
			
			if (img==null || img.length==0) return javax.ws.rs.core.Response.ok().build();
			
			return javax.ws.rs.core.Response.ok().entity(new StreamingOutput() {
				
				@Override
				public void write(OutputStream output) throws IOException,
						WebApplicationException {
					output.write(img);
		            output.flush();					
				}
			}).build();
			
		} catch (Exception e) {
			return javax.ws.rs.core.Response.ok().build();
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

			if (!userService.validateUser(getImage.auth.uuid, getImage.auth.senha)) {
				throw new InvalidUser();
			}
			
			String b64 = imageService.getImageBase64(getImage.idImagem);

			return new Gson().toJson(new ImageResponse(Response.OK, b64));
		} catch (Exception e) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
		
	}
}
