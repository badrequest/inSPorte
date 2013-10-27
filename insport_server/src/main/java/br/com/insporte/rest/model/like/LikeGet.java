package br.com.insporte.rest.model.like;

import java.io.Serializable;

import br.com.insporte.rest.model.auth.Auth;

public class LikeGet implements Serializable {

	private Auth auth;

	private String request;

	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}