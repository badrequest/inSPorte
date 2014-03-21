package br.com.badrequest.insporte.rest.model.like;

import java.io.Serializable;

import br.com.badrequest.insporte.rest.model.auth.Auth;

public class GetLike implements Serializable {

	public Auth auth;

	public String request;

	public String id;

}