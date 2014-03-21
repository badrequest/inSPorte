package br.com.badrequest.insporte.rest.model.image;

import java.io.Serializable;

import br.com.badrequest.insporte.rest.model.auth.Auth;
import br.com.badrequest.insporte.rest.model.questionario.Imagem;

public class ImagemRequest implements Serializable {

	public Auth auth;
	
	public String request;
	
	public Imagem imagem;
	
}
