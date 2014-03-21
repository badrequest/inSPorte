package br.com.badrequest.insporte.rest.model.auth;

import java.io.Serializable;

/**
 * Armazena a autenticacao do usuario juntamente com uma string para identificar a requisicao
 */
public class Authentication implements Serializable {

	private Auth auth;
	private String request;

	public Authentication() {
	}
	
	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
	
}

