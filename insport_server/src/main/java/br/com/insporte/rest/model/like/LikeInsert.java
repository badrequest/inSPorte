package br.com.insporte.rest.model.like;

import java.io.Serializable;

import br.com.insporte.rest.model.auth.Auth;

public class LikeInsert implements Serializable {

	private Auth auth;

	private String request;

	private Like like;
	
	public LikeInsert() {
		
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

	public Like getLike() {
		return like;
	}

	public void setLike(Like like) {
		this.like = like;
	}
	
}
