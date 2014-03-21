package br.com.badrequest.insporte.rest.model.image;

import java.io.Serializable;

import br.com.badrequest.insporte.rest.model.auth.Auth;

public class GetImage implements Serializable {

	public Auth auth;
	
	public Long idImagem;
}
