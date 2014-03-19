package br.com.badrequest.insporte.rest.model.questionario;

import java.io.Serializable;

import br.com.badrequest.insporte.rest.model.auth.Auth;

public class Questionario implements Serializable {

	public Auth auth;
	
	public String request;
	
	public Info info;
	
	public Resposta resposta;
	
	public Comentario comentario;
	
}
